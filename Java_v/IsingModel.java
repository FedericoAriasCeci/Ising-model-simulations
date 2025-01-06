import java.util.Random;

public class IsingModel {
    // Método para calcular la energía por partícula de la red
    public static double h(int[][] S) {
        int E = 0;
        int rows = S.length;
        int cols = S[0].length;

        // Recorremos la matriz de spins
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Sumamos la energía considerando las interacciones con los vecinos
                E += -S[i][j] * (
                        S[(i - 1 + rows) % rows][j] + // Vecino arriba (manejo de bordes)
                        S[i][(j - 1 + cols) % cols] + // Vecino izquierda
                        S[(i + 1) % rows][j] +       // Vecino abajo
                        S[i][(j + 1) % cols]         // Vecino derecha
                );
            }
        }
        return E / (2.0 * rows * cols); // Dividimos por 2 * número de spins
    }

    // Método para aplicar el algoritmo de Metropolis a la red
    public static float[] metropolis(int[][] S, double[] prob) {
        int rows = S.length;
        int cols = S[0].length;
        int L = rows * cols; // Número de spins en la red

        float dm = 0;
        float de = 0;

        Random random = new Random();

        // Generamos coordenadas aleatorias para los spins
        int[][] coordinates = new int[L][2];
        for (int i = 0; i < L; i++) {
            coordinates[i][0] = random.nextInt(rows);
            coordinates[i][1] = random.nextInt(cols);
        }

        // Aplicamos el algoritmo de Metropolis
        for (int[] cord : coordinates) {
            int x = cord[0];
            int y = cord[1];

            int ss = S[x][y]; // Spin actual

            // Calculamos el cambio en la energía al invertir el spin
            int dE = 2 * ss * (
                    S[(x - 1 + rows) % rows][y] +
                    S[x][(y - 1 + cols) % cols] +
                    S[(x + 1) % rows][y] +
                    S[x][(y + 1) % cols]
            );

            double p = dE > 0 ? prob[(dE / 4) - 1] : 1.0; // Probabilidad de aceptar el cambio

            // Verificamos si aceptamos el cambio
            if (dE <= 0 || random.nextDouble() < p) {
                S[x][y] = -ss; // Invertimos el spin
                dm += -2.0 * ss / L;
                de += dE / (double) L;
            }
        }
        float[] diferenciales = new float[2];
        diferenciales[0] = dm;
        diferenciales[1] = de;
        return diferenciales;
    }
}
