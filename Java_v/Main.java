import java.io.FileWriter;
import java.io.IOException; 

public class Main {
    public static void main(String[] args) {
        int[] sizes = {16}; // Diferentes tamaños de la red
        double beta = 1 / 2.8; // Beta = 1/T
        int nequilibrio = 100000; // Número de pasos Monte Carlo

        // Tabla de probabilidades
        double[] prob = {Math.exp(-4 * beta), Math.exp(-8 * beta)};

        for (int L : sizes) {
            int[][] S = new int[L][L];
            double[] magnetization = new double[nequilibrio];
            double[] energy = new double[nequilibrio];

            // Inicializamos la red con todos los spins en 1
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < L; j++) {
                    S[i][j] = 1;
                }
            }

            // Valores iniciales
            magnetization[0] = 1;
            energy[0] = IsingModel.h(S);

            // Simulación Monte Carlo
            for (int n = 1; n < nequilibrio; n++) {
                float[] dif = new float[2];
                dif =  IsingModel.metropolis(S, prob);
                magnetization[n] = magnetization[n - 1] + dif[0];
                energy[n] = energy[n - 1] + dif[1];
            }

            // Guardamos los resultados en un archivo CSV
            String filename = "Java_v/ising_L" + L + ".csv";
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write("Paso,Magnetización,Energía\n");
                for (int n = 0; n < nequilibrio; n++) {
                    writer.write(n + "," + magnetization[n] + "," + energy[n] + "\n");
                }
                System.out.println("Resultados guardados en " + filename);
            } catch (IOException e) {
                System.err.println("Error al guardar el archivo: " + e.getMessage());
            }
        }
    }
}
