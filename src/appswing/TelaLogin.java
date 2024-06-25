package appswing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import modelo.Usuario;
import regras_negocio.Fachada2;

public class TelaLogin {

	private JDialog frame;
	private JLabel label;
	private JLabel label_1;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel label_2;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
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
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Login");
		frame.setBounds(100, 100, 248, 215);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada2.inicializar();
				if(Fachada2.listarUsuarios().isEmpty()) {
					try {
						Fachada2.cadastrarUsuario("fausto", "1234");
						label_2.setText("usuario 'fausto' cadastrado");
					}
					catch(Exception ex) {
						label_2.setText("nao conseguiu criar usuario");
					}
				}
			}
			@Override
			public void windowClosed(WindowEvent e) {
				Fachada2.finalizar();
			}
		});

		label = new JLabel("usuario");
		label.setBounds(24, 34, 46, 14);
		frame.getContentPane().add(label);

		label_1 = new JLabel("senha");
		label_1.setBounds(24, 64, 46, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setText("fausto");
		textField.setBounds(98, 31, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("1234");
		textField_1.setBounds(98, 61, 86, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		label_2 = new JLabel("");
		label_2.setBounds(24, 151, 198, 14);
		frame.getContentPane().add(label_2);

		button = new JButton("entrar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = textField.getText();
				String senha = textField_1.getText();

				Usuario usu = Fachada2.localizarUsuario(nome,senha);

				if(usu!=null) {
					Fachada2.logado = usu;
					new TelaPrincipal();
					frame.dispose();
				}
				else
					label_2.setText("usuario ou senha incorreto");
			}
		});
		button.setBounds(69, 96, 89, 23);
		frame.getContentPane().add(button);
	}
}
