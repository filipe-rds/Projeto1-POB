package appswing;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingConstants;

import regras_negocio.Fachada;

public class TelaPrincipal {
	private JFrame frame;
	private JMenu mnVeiculo;
	private JMenu mnRegistro;
	private JMenu mnConsulta;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fachada.inicializar();
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
		frame.setTitle("Estacionamento - Sistema de Gestão");
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Estacionamento");
		frame.setBounds(100, 100, 450, 363);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label = new JLabel("");
		label.setFont(new Font("Tahoma", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setText("Inicializando...");
		label.setBounds(0, 0, 467, 302);
		ImageIcon imagem = new ImageIcon(getClass().getResource("/arquivos/imagem.png"));
		imagem = new ImageIcon(
				imagem.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
		label.setIcon(imagem);
		frame.getContentPane().add(label);
		frame.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnVeiculo = new JMenu("Veículo");
		mnVeiculo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new TelaVeiculo();
				} catch (Exception ex) {
					ex.printStackTrace();
					label.setText("Erro ao abrir a tela de veículos: " + ex.getMessage());
				}
			}
		});
		menuBar.add(mnVeiculo);

		mnRegistro = new JMenu("Registro");
		mnRegistro.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new TelaRegistro();
				} catch (Exception ex) {
					ex.printStackTrace();
					label.setText("Erro ao abrir a tela de registros: " + ex.getMessage());
				}
			}
		});
		menuBar.add(mnRegistro);

		mnConsulta = new JMenu("Arrecadação");
		mnConsulta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new TelaArrecadacao();
				} catch (Exception ex) {
					ex.printStackTrace();
					label.setText("Erro ao abrir a tela de arrecadação: " + ex.getMessage());
				}
			}
		});
		menuBar.add(mnConsulta);

		mnConsulta = new JMenu("Consultas");
		mnConsulta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new TelaConsulta();
				} catch (Exception ex) {
					ex.printStackTrace();
					label.setText("Erro ao abrir a tela de consultas: " + ex.getMessage());
				}
			}
		});
		menuBar.add(mnConsulta);

	
	}
}
