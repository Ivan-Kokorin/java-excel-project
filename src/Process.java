import java.io.File;
import java.util.HashMap;

public class Process implements Runnable {
    File selectedFile;
    InfoTable infoTable;
    private DbManager dbManager;
    private ExcelManager excelManager;
    private int sizeRowTable;
    private int column;
    Controller controller;

    public Process(File selectedFile) {
        this.selectedFile = selectedFile;
        this.infoTable = InfoTable.getInfoTable(selectedFile);
        dbManager = DbManager.getDbManager(); // - засинглтонить
        excelManager = infoTable.getExcelManager();
        sizeRowTable = infoTable.getSizeRowTable();
        column = infoTable.getColumn();
        controller = Controller.getController(View.getView());
        controller.setSizeProgress(sizeRowTable);
    }

    @Override
    public void run() {
        System.out.println("START!");
        HashMap<String, String> dbObject = dbManager.createDbObject();
        int row = 0;
        while (row < sizeRowTable) {
            controller.setValueProgressProgressBar(row);
            String nameProduct = excelManager.getNameProduct(0, row, column);
            System.out.println(nameProduct);
            String urlProduct = dbObject.get(nameProduct);
            if (urlProduct == null) {
                System.out.println("НЕ НАЙДЕН В БАЗЕ ДАННЫХ");
                row++;
                continue;
            }
            System.out.println(urlProduct);
            excelManager.setLinkProduct(0, row, column, nameProduct, urlProduct);
            row++;
        }
        excelManager.writeExcel();
        System.out.println("РАБОТА ПРОГРАММЫ ОКОНЧЕНА");
    }
}
