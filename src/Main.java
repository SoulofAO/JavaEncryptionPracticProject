//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;

public class Main {
    private static final String ALPHABET =
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ., ?";
    private static final int ALPHABET_LENGTH = ALPHABET.length();

    public static void main(String[] args)
    {

        JFrame frame = new JFrame("Caesar's Cipher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    public static int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int rowsB = b.length;
        int colsB = b[0].length;

        // Проверка на возможность умножения
        if (colsA != rowsB) {
            throw new IllegalArgumentException("Number of columns in A must equal number of rows in B");
        }

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    public static double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;

        if (n == 1) {
            return matrix[0][0];
        }

        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0.0; // Изменено на double

        for (int col = 0; col < n; col++) {
            double[][] subMatrix = new double[n - 1][n - 1];

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (j < col) {
                        subMatrix[i - 1][j] = matrix[i][j];
                    } else if (j > col) {
                        subMatrix[i - 1][j - 1] = matrix[i][j];
                    }
                }
            }

            determinant += Math.pow(-1, col) * matrix[0][col] * calculateDeterminant(subMatrix);
        }

        return determinant;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static double[][] convertIntMatrixToDoubleMatrix(int[][] intMatrix) {
        int rows = intMatrix.length;
        int cols = intMatrix[0].length;
        double[][] doubleMatrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                doubleMatrix[i][j] = (double) intMatrix[i][j]; // Приведение к типу Double
            }
        }

        return doubleMatrix;
    }


    public static double[][] inverseMatrix(int[][] matrix) {
        int n = matrix.length;
        double[][] augmented = new double[n][2 * n];

        // Создаем расширенную матрицу
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + n] = 1; // Единичная матрица
        }

        // Прямой ход метода Гаусса
        for (int i = 0; i < n; i++) {
            // Находим максимальный элемент в текущем столбце
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmented[k][i]) > Math.abs(augmented[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Меняем местами строки
            double[] temp = augmented[i];
            augmented[i] = augmented[maxRow];
            augmented[maxRow] = temp;

            // Нормализуем текущую строку
            double divisor = augmented[i][i];
            if (divisor == 0) {
                throw new IllegalArgumentException("Матрица не имеет обратной.");
            }

            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= divisor;
            }

            // Обнуляем элементы в остальных строках
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }

        // Извлекаем обратную матрицу
        double[][] inverse = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmented[i][j + n];
            }
        }

        return inverse;
    }

    public static int[] extendedGCD(int a, int b) {
        if (b == 0) {
            return new int[]{a, 1, 0}; // gcd(a, b), x, y
        } else {
            int[] result = extendedGCD(b, a % b);
            int gcd = result[0];
            int x1 = result[1];
            int y1 = result[2];
            int x = y1;
            int y = x1 - (a / b) * y1;
            return new int[]{gcd, x, y};
        }
    }

    public static int[][] findInverse(int[][] matrix, int mod) {
        int n = matrix.length;
        int[][] augmented = new int[n][2 * n];

        // Создаем расширенную матрицу [matrix | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + n] = 1; // Заполняем единичную матрицу
        }

        // Применяем метод Гаусса
        for (int i = 0; i < n; i++) {
            // Нахождение ведущего элемента
            int pivot = augmented[i][i];
            int pivotIndex = i;

            // Поиск ненулевого ведущего элемента
            while (pivot == 0 && pivotIndex < n) {
                pivotIndex++;
                if (pivotIndex < n) {
                    pivot = augmented[pivotIndex][i];
                }
            }

            if (pivot == 0) {
                return null; // Обратная матрица не существует
            }

            // Обмен строк
            if (pivotIndex != i) {
                for (int j = 0; j < 2 * n; j++) {
                    int temp = augmented[i][j];
                    augmented[i][j] = augmented[pivotIndex][j];
                    augmented[pivotIndex][j] = temp;
                }
            }

            // Приведение к единичной форме
            int invPivot = modInverse(pivot, mod);
            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] = (augmented[i][j] * invPivot) % mod;
                if (augmented[i][j] < 0) {
                    augmented[i][j] += mod;
                }
            }

            // Обнуление остальных элементов столбца
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    int factor = augmented[j][i];
                    for (int k = 0; k < 2 * n; k++) {
                        augmented[j][k] = (augmented[j][k] - factor * augmented[i][k]) % mod;
                        if (augmented[j][k] < 0) {
                            augmented[j][k] += mod;
                        }
                    }
                }
            }
        }

        // Извлечение обратной матрицы
        int[][] inverse = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = augmented[i][j + n];
            }
        }

        return inverse;
    }

    public static int modInverse(int a, int mod) {
        a = a % mod;
        for (int x = 1; x < mod; x++) {
            if ((a * x) % mod == 1) {
                return x;
            }
        }
        return -1; // Обратный элемент не найден
    }



    private static String encrypt(String text, String Key) {
        int keyLength = Key.length();
        int matrixLength = (int) Math.floor(Math.sqrt(keyLength));
        int[][] keyMatrix = new int[matrixLength][matrixLength];

        for (int i = 0; i < matrixLength; i++)
        {
            for (int j = 0; j < matrixLength; j++)
            {
                keyMatrix[i][j] = ALPHABET.indexOf( Key.charAt(i*matrixLength + j));
            }
        }
        String output_text = "";
        for (int k = 0; k < (int) Math.ceil((double) text.length() / (double) matrixLength); k++)
        {
            int[][] textMatrix = new int[1][matrixLength];
            for (int i = 0; i < matrixLength; i++)
            {
                int index = k * matrixLength + i;
                char character;
                if (index >= 0 && index < text.length()) {
                    character = text.charAt(index);
                } else {
                    character = ' ';
                }
                textMatrix[0][i] = ALPHABET.indexOf(character);
            }
            int[][] multiplyMatrix = multiplyMatrices(textMatrix, keyMatrix);

            for (int i = 0; i<matrixLength; i++)
            {
                int result_index = multiplyMatrix[0][i]%(ALPHABET.length());
                output_text = output_text + ALPHABET.charAt(result_index);
            }
        }

        return output_text;
    }

    private static String decrypt(String text, String Key)
    {
        int keyLength = Key.length();
        int matrixLength = (int) Math.floor(Math.sqrt(keyLength));
        int[][] keyMatrix = new int[matrixLength][matrixLength];

        for (int i = 0; i < matrixLength; i++)
        {
            for (int j = 0; j < matrixLength; j++)
            {
                keyMatrix[i][j] = ALPHABET.indexOf( Key.charAt(i*matrixLength + j));
            }
        }
        int[][] inverseKeyMatrix = findInverse(keyMatrix, ALPHABET.length());
        String output_text = "";
        for (int k = 0; k < (int) Math.ceil((double) text.length() / (double) matrixLength); k++)
        {
            int[][] textMatrix = new int[1][matrixLength];
            for (int i = 0; i < matrixLength; i++)
            {
                int index = k * matrixLength + i;
                char character;
                if (index >= 0 && index < text.length()) {
                    character = text.charAt(index);
                } else {
                    character = ' ';
                }
                textMatrix[0][i] = ALPHABET.indexOf(character);
            }
            int[][] multiplyMatrix = multiplyMatrices(textMatrix, inverseKeyMatrix);

            for (int i = 0; i<matrixLength; i++)
            {
                int result_index = multiplyMatrix[0][i]%(ALPHABET.length());
                output_text = output_text + ALPHABET.charAt(result_index);
            }
        }

        return output_text;
    }

    static class CustomAlthabetDocumentFilter extends DocumentFilter {
        private final String allowedCharacters;

        public CustomAlthabetDocumentFilter(String allowedCharacters) {
            this.allowedCharacters = allowedCharacters;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isAllowed(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isAllowed(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isAllowed(String string) {
            for (char c : string.toCharArray()) {
                if (allowedCharacters.indexOf(c) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    private static void placeComponents(JPanel panel) {

        JCheckBox decryptCheckBox = new JCheckBox("Decrypt");
        panel.add(decryptCheckBox);

        JTextField textField = new JTextField(20);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new CustomAlthabetDocumentFilter(ALPHABET));
        textField.setText("ШИФР");
        panel.add(textField);

        JLabel outputLabel = new JLabel("");
        panel.add(outputLabel);

        JTextField keyField = new JTextField(20);
        ((AbstractDocument) keyField.getDocument()).setDocumentFilter(new CustomAlthabetDocumentFilter(ALPHABET));
        keyField.setText("АЛЬПИНИЗМ");
        panel.add(keyField );

        JButton button = new JButton("Encript Text");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                String keyString = keyField.getText();
                String outputText = "Some Go Wrong";
                if(!decryptCheckBox.isSelected())
                {
                    outputText = encrypt(inputText, keyString);
                }
                else {
                    outputText = decrypt(inputText, keyString);
                }
                outputLabel.setText(outputText);
            }
        });
    }
}