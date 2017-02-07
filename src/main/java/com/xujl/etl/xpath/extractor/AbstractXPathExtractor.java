package com.xujl.etl.xpath.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xujl.etl.xpath.extractor.Extractor.Callback.FieldEntry;
import com.xujl.etl.xpath.extractor.Extractor.Callback.ModelEntry;
import com.xujl.etl.xpath.kit.K;
import com.xujl.etl.xpath.kit.Properties;
import com.xujl.etl.xpath.schema.Field;
import com.xujl.etl.xpath.schema.Model;

/**
 * 抽象XPath解析 , 所有支持XPath的解析 都要继承此类
 *
 * @author 赖伟  l.weiwei@163.com 2016-01-12
 */
public abstract class AbstractXPathExtractor extends Extractor {

    protected AbstractXPathExtractor( String page, Model... models) {
        super( page, models);
    }

    protected abstract Object getDoc();

    protected abstract List<Object> extractModel(Object doc, String modelXpath);

    protected abstract List<Object> extractField(Object model, Field field, String xpath, String attr, boolean isSerialize);

    protected abstract Map<String, String> extractAttributes(Object node);

    public void extract(Callback callback) {
        List<Model> models = getModels();
        if (K.isEmpty(models)) {
            throw new  RuntimeException("请添加抽取模型配置");
        }

        models.forEach(model -> {
            final String modelXpath = model.getString("xpath");
            final Object doc = getDoc();
            List<Object> mNodes = new ArrayList<>();
            if (K.isNotBlank(modelXpath)) {
                // 抽取模型
                List<Object> mds = this.extractModel(doc, modelXpath);
                if (mds != null) {
                    mNodes.addAll(mds);
                }
            } else {
                mNodes.add(doc);
            }
            mNodes.forEach(mNode -> {
                final Properties fields = this.extractModel(mNode, model, callback);
                // 通知回调
                callback.onModelExtracted(new ModelEntry(model, fields));
            });
        });
    }

    private Properties extractModel(Object mNode, Model model, Callback callback) {
        final Properties fields = new Properties();
        if (model.getBoolean("isAutoExtractAttrs", false)) {
            // extract all attributes
            Map<String, String> attrs = this.extractAttributes(mNode);
            if (attrs != null) {
                attrs.forEach((k, v) -> {
                    fields.put(k, v);
                });
            }
        }
        model.getFields().forEach(field -> {
            final String xpath = field.getString("xpath", ".");
            final String attr = field.getString("attribute", field.getString("attr"));
            final boolean isSerialize = field.getBoolean("isSerialize", false);
            final boolean isAutoExtractAttrs = field.getBoolean("isAutoExtractAttrs", false);
            // 抽取字段
            List<Object> values = this.extractField(mNode, field, xpath, attr, isSerialize);
            if (K.isEmpty(values)) {
                return;
            }
            // 若字段包含子字段，则进入递归抽取
            if (isAutoExtractAttrs || K.isNotEmpty(field.getFields())) {
                List<Object> subValues = new ArrayList<>();
                values.forEach(node -> {
                    Properties _fields = this.extractModel(node, field.toModel(), callback);
                    subValues.add(_fields);
                });
                values = subValues;
            }

            final boolean isArray = field.getBoolean("isArray", false);
            final FieldEntry entry = new FieldEntry(field, values);
            entry.setData(isArray ? values : values.get(0));
            callback.onFieldExtracted(entry);
            fields.put(field.getName(), entry.getData());
        });
        return fields;
    }

}
