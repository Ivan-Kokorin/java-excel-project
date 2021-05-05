import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class View extends JFrame {
    private static View view;
    static JProgressBar progressBar;
    static JButton btnSaveFile = null;
    static JButton btnOpenDir = null;
    static JButton btnFileFilter = null;
    static JPanel contents = null;
    static JPanel contentBar = null;
    static JPanel contentButton = null;
    File selectedFile;

    private JFileChooser fileChooser = null;
    private final String[][] FILTERS = {{"xls", "Файлы Excel (*.xls)"},
            {"pdf", "Adobe Reader(*.pdf)"}};

    private View() {
        super("Создать ссылки для прайс-листа");

        // Локализация компонентов окна JFileChooser
        UIManager.put(
                "FileChooser.saveButtonText", "Сохранить");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Кнопка создания диалогового окна для сохранения файла
        btnSaveFile = new JButton("Выбрать файл");
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setString("ВЫПОЛНЕНИЕ ПРОГРАММЫ");

        // Создание экземпляра JFileChooser
        fileChooser = new JFileChooser();
        // Подключение слушателей к кнопкам
//        addFileChooserListeners();
        //строка прогресса

        btnSaveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Выбор файла");
                // Определение режима - только файл
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(View.this);
                // Если файл выбран, то представим его в сообщении
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(View.this,
                            "Файл '" + fileChooser.getSelectedFile() +
                                    " ' выбран");
                    btnSaveFile.setEnabled(false);
                    new Thread(new Process(selectedFile)).start();
                }
            }
        });

        // Размещение кнопок в интерфейсе
        contents = new JPanel();
        contentButton = new JPanel();
        contentBar = new JPanel();
        contents.setLayout(new GridLayout(2, 1));
        contentButton.add(btnSaveFile);
        contentBar.add(progressBar);
        contents.add(contentButton);
        contents.add(contentBar);
//        contents.add(btnFileFilter);
        setContentPane(contents);

        // Вывод окна на экран
        setSize(360, 110);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static View getView() {
        if (view == null) {
            view = new View();
            return view;
        }
        return view;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void changeProgressBar(int row) {
        System.out.println("Прогресс " + row);
        progressBar.setValue(row + 1);
    }
}
