//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.*;


// Код базируется на статье: https://habr.com/ru/articles/332714/
// Код подготовлен студентами 441 группы, Афанасьевым Олегом, Григорием Сергиенко, Максимом Федорущенко.

public class Main {
    private static final String ALPHABET =
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ., ?";
    //Число символов должно быть равно простому числу. Иначе код усложняется. Это не обязательно, но удобно.

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

        // Проверяем, можем ли вообще умножать эти две матрицы
        if (colsA != rowsB) {
            throw new IllegalArgumentException("Число столбцов в A должно совпадать с числом строк в B");
        }

        // Результат умножения будет иметь размерность строк A и столбцов B
        int[][] result = new int[rowsA][colsB];

        // Классический тройной цикл для умножения матриц
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    // Накопление суммы произведений соответствующих элементов
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    public static double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;

        // Если матрица размером 1x1, её определитель — это единственный элемент
        if (n == 1) {
            return matrix[0][0];
        }

        // Для матриц 2x2 используем простую формулу
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }

        double determinant = 0.0;

        // Разворачиваем определитель по первой строке (метод разложения по минорам)
        for (int col = 0; col < n; col++) {
            // Создаем подматрицу для миноров, исключая текущий столбец
            double[][] subMatrix = new double[n - 1][n - 1];

            for (int i = 1; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Копируем элементы, пропуская текущий столбец
                    if (j < col) {
                        subMatrix[i - 1][j] = matrix[i][j];
                    } else if (j > col) {
                        subMatrix[i - 1][j - 1] = matrix[i][j];
                    }
                }
            }

            // Суммируем элементы по правилу разложения Лапласа
            determinant += Math.pow(-1, col) * matrix[0][col] * calculateDeterminant(subMatrix);
        }

        return determinant;
    }

    public static void printMatrix(int[][] matrix) {
        // Просто выводим каждую строку матрицы
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

        // Преобразуем каждое целое значение в double, чтобы работать с ним как с плавающей точкой
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                doubleMatrix[i][j] = (double) intMatrix[i][j];
            }
        }

        return doubleMatrix;
    }

    public static double[][] inverseMatrix(int[][] matrix) {
        int n = matrix.length;
        double[][] augmented = new double[n][2 * n];

        // Формируем расширенную матрицу, добавляем единичную матрицу справа
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented[i][j] = matrix[i][j];
            }
            augmented[i][i + n] = 1; // Заполняем единичную матрицу
        }

        // Прямой ход метода Гаусса для приведения к треугольному виду
        for (int i = 0; i < n; i++) {
            // Ищем строку с наибольшим элементом в текущем столбце для лучшей устойчивости
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(augmented[k][i]) > Math.abs(augmented[maxRow][i])) {
                    maxRow = k;
                }
            }

            // Меняем строки местами, если это нужно
            double[] temp = augmented[i];
            augmented[i] = augmented[maxRow];
            augmented[maxRow] = temp;

            // Нормализуем строку, делим на ведущий элемент
            double divisor = augmented[i][i];
            if (divisor == 0) {
                throw new IllegalArgumentException("Матрица не имеет обратной.");
            }

            for (int j = 0; j < 2 * n; j++) {
                augmented[i][j] /= divisor;
            }

            // Обнуляем остальные строки по текущему столбцу
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented[k][i];
                    for (int j = 0; j < 2 * n; j++) {
                        augmented[k][j] -= factor * augmented[i][j];
                    }
                }
            }
        }

        // Извлекаем правую часть матрицы, которая теперь является обратной
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
    /*
        Обратная матрица по модулю — это матрица, которая, будучи умноженной на исходную матрицу по модулю некоторого числа n, дает единичную матрицу. Формально это выражается как:


            A · A^-1≡ I    (mod  n)


        где:

        - A — исходная матрица,
        - A^-1 — её обратная матрица по модулю n,
        - I — единичная матрица,
        - n — модуль, по которому выполняются все операции.

        ▎Основные понятия

        - Единичная матрица — это квадратная матрица, у которой все диагональные элементы равны 1, а все остальные элементы равны 0.
        - Обратная матрица — это такая матрица A^-1, которая при умножении на исходную матрицу A дает единичную матрицу I, т.е. A · A^-1 = I.
        - Обратная матрица по модулю — здесь умножение и деление элементов матриц осуществляется по правилам модульной арифметики.

        ▎Шаги нахождения обратной матрицы по модулю

        1. Проверка существования обратной матрицы
           - Не для всех матриц можно найти обратную по модулю. Перед поиском обратной матрицы нужно убедиться в том, что её определитель существует и обратим по модулю n.

           1.1. Вычисление определителя: Для квадратной матрицы A необходимо найти её определитель det(A).

           1.2. Проверка взаимной простоты: Определитель det(A) должен быть взаимно простым с модулем n (т.е. их наибольший общий делитель должен быть равен 1: gcd(det(A), n) = 1). Если это условие не выполняется, обратной матрицы не существует.

        2. Нахождение обратного числа по модулю
           - Чтобы найти обратную матрицу, нужно уметь находить обратное число по модулю. Это значит, что для каждого элемента матрицы нужно найти число b, которое удовлетворяет условию:


            a · b ≡ 1    (mod  n)


           Такое число b можно найти с помощью расширенного алгоритма Евклида.

           Пример: Если мы ищем обратное к числу 3 по модулю 7, нужно найти b, которое удовлетворяет 3 · b ≡ 1    (mod  7). Решением является b = 5, потому что 3 · 5 = 15 и 15  7 = 1.

        3. Создание дополнительной матрицы (матрица алгебраических дополнений)
           - После того как вы убедились, что матрица обратима по модулю, можно приступить к вычислению обратной матрицы.

           3.1. Матрица алгебраических дополнений: Для каждого элемента матрицы A нужно найти его алгебраическое дополнение — это минор элемента, умноженный на соответствующий знак (-1)^i+j, где i и j — индексы строки и столбца.

           3.2. Транспонирование матрицы дополнений: После нахождения всех алгебраических дополнений их собирают в матрицу и транспонируют (меняют строки и столбцы местами).

        4. Нахождение обратной матрицы по модулю
           - Теперь можно собрать обратную матрицу:

           4.1. Деление всех элементов на определитель: Обратная матрица равна транспонированной матрице дополнений, умноженной на обратное к определителю по модулю n. Это значит, что каждый элемент транспонированной матрицы нужно умножить на обратное к определителю по модулю n.

           4.2. Операции по модулю: Все вычисления ведутся по модулю n. Поэтому после каждой арифметической операции (умножения и деления) нужно брать остаток от деления на n.

        ▎Пример нахождения обратной матрицы по модулю

        Допустим, у нас есть матрица A и модуль n = 5:


            A =
            [ 2 & 3; 1 & 4 ]


        Шаг 1: Вычисление определителя.


            det(A) = 2 · 4 - 3 · 1 = 8 - 3 = 5


        Так как 5  5 = 0, обратной матрицы по модулю 5 не существует. Если бы определитель был равен 1 или 3, мы могли бы продолжить процесс.
 */
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



    // Функция шифрования текста с использованием матрицы ключа.
    // Использует модульное умножение матрицы текста на матрицу ключа.
    private static String encrypt(String text, String Key) {
        int keyLength = Key.length();
        int matrixLength = (int) Math.floor(Math.sqrt(keyLength)); // Определяем размер матрицы ключа (например, 3x3)
        int[][] keyMatrix = new int[matrixLength][matrixLength]; // Инициализируем матрицу для ключа

        // Заполняем матрицу ключа числовыми значениями, используя индексы символов из алфавита
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                keyMatrix[i][j] = ALPHABET.indexOf(Key.charAt(i * matrixLength + j)); // Переводим символы в числовые индексы
            }
        }

        String output_text = ""; // Строка для хранения зашифрованного текста

        // Шифруем текст по частям, разбивая его на блоки размером matrixLength
        for (int k = 0; k < (int) Math.ceil((double) text.length() / (double) matrixLength); k++) {
            int[][] textMatrix = new int[1][matrixLength]; // Матрица для текущего блока текста

            // Заполняем текстовую матрицу символами из строки
            for (int i = 0; i < matrixLength; i++) {
                int index = k * matrixLength + i; // Рассчитываем индекс символа в строке
                char character;
                if (index >= 0 && index < text.length()) {
                    character = text.charAt(index); // Берём символ из строки
                } else {
                    character = ' '; // Если символов не хватает, заполняем пробелами
                }
                textMatrix[0][i] = ALPHABET.indexOf(character); // Переводим символ в его числовой индекс
            }

            // Умножаем текстовую матрицу на матрицу ключа для шифрования
            int[][] multiplyMatrix = multiplyMatrices(textMatrix, keyMatrix);

            // Переводим результат умножения обратно в символы алфавита
            for (int i = 0; i < matrixLength; i++) {
                int result_index = multiplyMatrix[0][i] % ALPHABET.length(); // Берём остаток по модулю длины алфавита
                output_text = output_text + ALPHABET.charAt(result_index); // Добавляем символ в выходную строку
            }
        }

        return output_text; // Возвращаем зашифрованный текст
    }

    // Функция дешифровки текста, работает аналогично шифрованию, но использует обратную матрицу ключа.
    private static String decrypt(String text, String Key) {
        int keyLength = Key.length();
        int matrixLength = (int) Math.floor(Math.sqrt(keyLength)); // Определяем размер матрицы ключа
        int[][] keyMatrix = new int[matrixLength][matrixLength]; // Инициализируем матрицу для ключа

        // Заполняем матрицу ключа так же, как и при шифровании
        for (int i = 0; i < matrixLength; i++) {
            for (int j = 0; j < matrixLength; j++) {
                keyMatrix[i][j] = ALPHABET.indexOf(Key.charAt(i * matrixLength + j));
            }
        }

        // Находим обратную матрицу для ключа по модулю длины алфавита
        int[][] inverseKeyMatrix = findInverse(keyMatrix, ALPHABET.length());
        String output_text = ""; // Строка для хранения расшифрованного текста

        // Разбиваем зашифрованный текст на блоки и дешифруем каждый блок
        for (int k = 0; k < (int) Math.ceil((double) text.length() / (double) matrixLength); k++) {
            int[][] textMatrix = new int[1][matrixLength]; // Матрица для текущего блока зашифрованного текста

            // Заполняем текстовую матрицу числовыми индексами символов
            for (int i = 0; i < matrixLength; i++) {
                int index = k * matrixLength + i;
                char character;
                if (index >= 0 && index < text.length()) {
                    character = text.charAt(index); // Берём символ из текста
                } else {
                    character = ' '; // Заполняем пробелами, если длина текста меньше
                }
                textMatrix[0][i] = ALPHABET.indexOf(character); // Преобразуем символ в его числовое значение
            }

            // Умножаем текстовую матрицу на обратную матрицу ключа для дешифровки
            int[][] multiplyMatrix = multiplyMatrices(textMatrix, inverseKeyMatrix);

            // Преобразуем результат обратно в символы алфавита
            for (int i = 0; i < matrixLength; i++) {
                int result_index = multiplyMatrix[0][i] % ALPHABET.length(); // Остаток по модулю алфавита
                output_text = output_text + ALPHABET.charAt(result_index); // Добавляем символ в результат
            }
        }

        return output_text; // Возвращаем расшифрованный текст
    }

    // Фильтр для текстового поля, который разрешает ввод только символов из заданного алфавита.
    static class CustomAlthabetDocumentFilter extends DocumentFilter {
        private final String allowedCharacters; // Разрешенные символы

        // Конструктор принимает строку с разрешёнными символами
        public CustomAlthabetDocumentFilter(String allowedCharacters) {
            this.allowedCharacters = allowedCharacters;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isAllowed(string)) { // Если все символы разрешены, вставляем текст
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isAllowed(text)) { // Если все символы разрешены, заменяем текст
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length); // Просто удаляем текст без проверки
        }

        // Метод проверки, что все символы в строке принадлежат разрешенному алфавиту
        private boolean isAllowed(String string) {
            for (char c : string.toCharArray()) {
                if (allowedCharacters.indexOf(c) == -1) { // Если символ не найден в разрешённых, возвращаем false
                    return false;
                }
            }
            return true; // Все символы допустимы
        }
    }

    // Основная функция инициализации Widgets.
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

                int length = keyString.length();
                double sqrt = Math.sqrt(length);
                if (sqrt != Math.floor(sqrt)) { // Проверяем, является ли корень целым числом
                    outputText = "ERROR: The key size is must the square of the number.";
                }
                else {
                    if (!decryptCheckBox.isSelected()) {
                        outputText = encrypt(inputText, keyString);
                    } else {
                        outputText = decrypt(inputText, keyString);
                    }
                }
                outputLabel.setText(outputText);
            }
        });
    }
}