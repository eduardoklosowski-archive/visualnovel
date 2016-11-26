package br.eduardoklosowski.visualnovel;

public class Main {
    public static void main(String[] args) {
        Database.startDatabase();
        Engine engine = new Engine();
        engine.run();
    }
}
