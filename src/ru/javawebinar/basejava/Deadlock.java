package ru.javawebinar.basejava;

public class Deadlock {
    private static boolean isStopped = false;
    public static void main(String[] args) {
        Knight knight1 = new Knight();
        Knight knight2 = new Knight();

        Thread thread1 = new Thread(() -> {
            while (!isStopped) {
                fight(knight1, knight2);
            }
        });

        Thread thread2 = new Thread(() -> {
            while (!isStopped) {
                fight(knight2, knight1);
            }
        });

        thread1.start();
        thread2.start();

    }

    private static void fight(Knight knight1, Knight knight2) {
        synchronized (knight1) {
            synchronized (knight2) {
                knight1.health -= (int) (Math.random() * 10);
                knight2.health -= (int) (Math.random() * 10);
                if (knight1.health <= 0 || knight2.health <= 0) {
                    isStopped = true;
                    System.out.println("Game over");
                }
            }
        }
    }

    public static class Knight{
        public int health = 100;
    }

}


