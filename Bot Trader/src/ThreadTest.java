public class ThreadTest extends Thread {
    private String nome;

    public ThreadTest(String nome) {
        this.nome = nome;
        start();
    }

    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println(nome + " " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
