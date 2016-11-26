package br.eduardoklosowski.visualnovel;

import java.util.ArrayList;
import java.util.List;

public class History {
    private long id;
    private String text;
    private List<Long> options;

    public History(long id, String text, List<Long> options) {
        this.id = id;
        this.text = text;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<Long> getOptionsId() {
        return options;
    }

    public List<Option> getOptions() {
        List<Option> list = new ArrayList<>();
        OptionDAO optionDAO = new OptionDAO();
        for (long optionId : options) {
            list.add(optionDAO.get(optionId));
        }
        return list;
    }
}
