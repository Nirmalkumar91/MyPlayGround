package com.nish.android.playground.addressbook;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

public class ResourceTypeConverter implements Converter<String> {

    @Override
    public String read(InputNode node) throws Exception {
        return node.getName();
    }

    @Override
    public void write(OutputNode node, String value) throws Exception {

    }
}
