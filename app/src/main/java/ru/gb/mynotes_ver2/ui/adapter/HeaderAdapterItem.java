package ru.gb.mynotes_ver2.ui.adapter;

public class HeaderAdapterItem implements AdapterItem{
    private String header;

    public HeaderAdapterItem(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

}
