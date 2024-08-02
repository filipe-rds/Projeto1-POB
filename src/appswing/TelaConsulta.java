package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Registro;
import modelo.Veiculo;
import regras_negocio.Fachada;

public class TelaConsulta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JLabel label;
    private JLabel label_4;

    /**
     * Create the application.
     */
    public TelaConsulta() {
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
        frame.setTitle("Consulta");
        frame.setBounds(100, 100, 729, 385);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
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
        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
        table.setFocusable(false);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel(""); // label de mensagem
        label.setForeground(Color.BLUE);
        label.setBounds(21, 321, 688, 14);
        frame.getContentPane().add(label);

        label_4 = new JLabel("Resultados:");
        label_4.setBounds(21, 190, 431, 14);
        frame.getContentPane().add(label_4);

        JButton btnVeiculosNaData = new JButton("Veículos na Data");
        btnVeiculosNaData.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVeiculosNaData.setBounds(21, 10, 160, 23);
        btnVeiculosNaData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

				try{
                String data = JOptionPane.showInputDialog("Digite a data (dd/MM/yyyy):");
                if (data != null) {
                    List<Veiculo> resultado = Fachada.veiculosNaData(data);
                    listagemVeiculo(resultado);
                }}
				catch(Exception erro){
					JOptionPane.showMessageDialog(null, erro.getMessage());
            }
        }});
        frame.getContentPane().add(btnVeiculosNaData);

        JButton btnVeiculosAcimaRegistro = new JButton("Veículos com Mais de N Registros");
        btnVeiculosAcimaRegistro.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnVeiculosAcimaRegistro.setBounds(191, 10, 250, 23);
        btnVeiculosAcimaRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String n = JOptionPane.showInputDialog("Digite N:");
                if (n != null) {
                    try {
                        int numero = Integer.parseInt(n);
						
                        List<Veiculo> resultado = Fachada.veiculosAcimaDoRegistro(numero);
                        listagemVeiculo(resultado);
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }
            }
        });
        frame.getContentPane().add(btnVeiculosAcimaRegistro);

        JButton btnRegistrosNaData = new JButton("Registros na Data");
        btnRegistrosNaData.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnRegistrosNaData.setBounds(451, 10, 160, 23);
        btnRegistrosNaData.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = JOptionPane.showInputDialog("Digite a data (dd/MM/yyyy):");
                if (data != null) {
					try{
                    List<Registro> resultado = Fachada.registrosNaData(data);
                    listagemRegistro(resultado);}
					catch(Exception erro){
						JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        }
	});
        frame.getContentPane().add(btnRegistrosNaData);
    }

    public void listagemVeiculo(List<Veiculo> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Placa");

            for (Veiculo veiculo : lista) {
                model.addRow(new Object[] { veiculo.getPlaca() });
            }

            table.setModel(model);
            label_4.setText("Resultados: " + lista.size() + " veículos");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }

    public void listagemRegistro(List<Registro> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID");
            model.addColumn("Placa");
            model.addColumn("Tipo");

            for (Registro registro : lista) {
                model.addRow(new Object[] { registro.getId(), registro.getVeiculo().getPlaca(), registro.getTipo() });
            }

            table.setModel(model);
            label_4.setText("Resultados: " + lista.size() + " registros");
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
}
