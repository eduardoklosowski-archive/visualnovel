package br.eduardoklosowski.visualnovel;

public class Option {
    private long id;
    private String text;
    private long nextId;

    public Option(long id, String text, long nextId) {
        this.id = id;
        this.text = text;
        this.nextId = nextId;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public long getNextId() {
        return nextId;
    }

    public History getNext() {
        HistoryDAO historyDAO = new HistoryDAO();
        return historyDAO.get(nextId);
    }
}
