package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import modelo.Arrecadacao;
import regras_negocio.Fachada;

public class TelaArrecadacao {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField textField;
    private JLabel label;
    private JLabel label_2;
    private JLabel label_4;

    private JButton button_1;
    private JButton button_2;
    private JButton button_3;

    /**
     * Create the application.
     */
    public TelaArrecadacao() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Arrecadação");
        frame.setBounds(100, 100, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    Fachada.inicializar();
                    listagem();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao inicializar: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Fachada.finalizar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao finalizar: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 43, 674, 148);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (table.getSelectedRow() >= 0)
                    label_4.setText("Selecionado: " + table.getValueAt(table.getSelectedRow(), 0));
            }
        });
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(new Color(144, 238, 144));
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel(""); // label de mensagem
        label.setBounds(21, 310, 431, 14);
        frame.getContentPane().add(label);

        label_4 = new JLabel("Resultados:");
        label_4.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(label_4);

        label_2 = new JLabel("Data:");
        label_2.setHorizontalAlignment(SwingConstants.LEFT);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label_2.setBounds(12, 269, 89, 14);
        frame.getContentPane().add(label_2);

        textField = new JTextField();
        textField.setFont(new Font("Dialog", Font.PLAIN, 12));
        textField.setColumns(10);
        textField.setBounds(103, 266, 195, 20);
        frame.getContentPane().add(textField);

        button_1 = new JButton("Cadastrar Arrecadação");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (textField.getText().isEmpty()) {
                        label.setText("Campo vazio");
                        return;
                    }
                    String data = textField.getText();
                    Fachada.cadastrarArrecadacao(data);
                    label.setText("Arrecadação cadastrada: " + data);
                    listagem();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_1.setBounds(525, 265, 153, 23);
        frame.getContentPane().add(button_1);

        button_2 = new JButton("Excluir Selecionado");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) {

                        // Obter o valor da célula, assumindo que é um LocalDate
                        LocalDate data = (LocalDate) table.getValueAt(table.getSelectedRow(), 0);

                        // Definir um formatador para converter LocalDate em String
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        // Converter LocalDate para String
                        String dataString = data.format(formatter);
                        Fachada.excluirArrecadacao(dataString);
                        label.setText("Arrecadação excluída: " + data);
                        listagem();
                    } else
                        label.setText("Nenhuma arrecadação selecionada");
                        listagem();
                } catch (Exception ex) {
                    label.setText(ex.getMessage());
                }
            }
        });
        button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_2.setBounds(281, 213, 171, 23);
        frame.getContentPane().add(button_2);

        button_3 = new JButton("Listar Arrecadações");
        button_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    listagem();
                } catch (Exception ex) {
                    label.setText("Erro ao listar arrecadações: " + ex.getMessage());
                }
            }
        });
        button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button_3.setBounds(308, 11, 171, 23);
        frame.getContentPane().add(button_3);
    }

    public void listagem() {
        try {
            List<Arrecadacao> lista = Fachada.listarArrecadacoes();
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Data");
            model.addColumn("Total");

            for (Arrecadacao arrecadacao : lista) {
                model.addRow(new Object[] { arrecadacao.getData(), arrecadacao.getTotal() });
            }

            table.setModel(model);
            label_4.setText("Resultados: " + lista.size() + " arrecadações");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
