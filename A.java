private void enemySpaceShipMove() {
        new Thread(() -> {
            while (true) {
               ArrayList<EnemySpaceShip> toRemove = new ArrayList<>();
                for (EnemySpaceShip enemySpaceShip : enemySpaceShips) {
                    if (enemySpaceShip.getY() < 100) {
                        enemySpaceShip.moveDown();
                    } else {
                        enemySpaceShip.moveSideways();
                    }
                    enemySpaceShip.shoot();
                    enemySpaceShip.bulletsMove();

                    if (enemySpaceShip.getY() >= 900) {
                        toRemove.add(enemySpaceShip);
                    }
                }
                    enemySpaceShips.removeAll(toRemove);
                repaint();
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void enemySpaceShipSpawner() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 0; i < 1; i++) {
                    enemySpaceShips.add(new EnemySpaceShip(300 + i * 300, -20));
                }
            }
        }).start();
    }