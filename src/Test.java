import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Test extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Container c;
	private JLabel title, flb, ilb, plb, glb;
	private JTextField fnTf, inTf, pTf, gTf;
	private JButton add, update, delete, clear;
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private String[] colume = { "First name", "Last name", "Phone number", "CGPA" };
	private String first, last, phone, cgpa;

	Test() {
		Apurba();
		Database();
	}

	public void Apurba() {

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(780, 690);
		this.setLocationRelativeTo(null);
		this.setTitle("Students Table");

		c = this.getContentPane();
		c.setLayout(null);
		c.setBackground(Color.ORANGE);
		Font f = new Font("Arial", Font.BOLD, 16);

		title = new JLabel("Student Registration");
		title.setFont(f);
		title.setBounds(140, 10, 250, 50);
		c.add(title);

		flb = new JLabel("First Name");
		flb.setFont(f);
		flb.setBounds(10, 80, 200, 30);
		c.add(flb);

		fnTf = new JTextField();
		fnTf.setBounds(110, 80, 200, 30);
		fnTf.setFont(f);
		c.add(fnTf);

		add = new JButton("Add");
		add.setBounds(400, 80, 150, 30);
		add.setFont(f);
		add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		c.add(add);

		ilb = new JLabel("Last name   ");
		ilb.setBounds(10, 130, 150, 30);
		ilb.setFont(f);
		c.add(ilb);

		inTf = new JTextField();
		inTf.setBounds(110, 130, 200, 30);
		inTf.setFont(f);
		c.add(inTf);

		update = new JButton("Update");
		update.setBounds(400, 130, 150, 30);
		update.setFont(f);
		update.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		c.add(update);

		plb = new JLabel("Phone   ");
		plb.setBounds(10, 180, 150, 30);
		plb.setFont(f);
		c.add(plb);

		pTf = new JTextField();
		pTf.setBounds(110, 180, 200, 30);
		pTf.setFont(f);
		c.add(pTf);

		delete = new JButton("Delete  ");
		delete.setBounds(400, 180, 150, 30);
		delete.setFont(f);
		delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		c.add(delete);

		glb = new JLabel("CGPA  ");
		glb.setBounds(10, 230, 150, 30);
		glb.setFont(f);
		c.add(glb);

		gTf = new JTextField();
		gTf.setBounds(110, 230, 200, 30);
		gTf.setFont(f);
		c.add(gTf);

		clear = new JButton("Clear  ");
		clear.setBounds(400, 230, 150, 30);
		clear.setFont(f);
		clear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		c.add(clear);

		table = new JTable();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(colume);
		table.setModel(model);
		table.setSelectionBackground(Color.RED);
		table.setBackground(Color.WHITE);
		table.setRowHeight(30);

		scroll = new JScrollPane(table);
		scroll.setBounds(10, 360, 740, 265);

		add.addActionListener(this);
		clear.addActionListener(this);
		delete.addActionListener(this);
		update.addActionListener(this);
	}

	public void Database() {
		try {
			Connection con = DBConnection.getConnection();
			Statement st = con.createStatement();
			st.executeUpdate("use swann");
			String query = "select * from students";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			c.add(scroll);

		} catch (Exception e4) {
			e4.printStackTrace();
			JOptionPane.showMessageDialog(null, "Problem");
		}
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				int row = table.getSelectedRow();

				first = table.getValueAt(row, 0).toString();
				last = table.getValueAt(row, 1).toString();
				phone = table.getValueAt(row, 2).toString();
				cgpa = table.getValueAt(row, 3).toString();

				fnTf.setText(first);
				inTf.setText(last);
				pTf.setText(phone);
				gTf.setText(cgpa);
			}
		});
	}

	public static void main(String[] args) {
		Test frame = new Test();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			try {
				Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				st.executeUpdate("use swann");
				st.executeUpdate("insert into students values ('" + fnTf.getText() + "','" + inTf.getText() + "','"
						+ pTf.getText() + "','" + gTf.getText() + "')");

				JOptionPane.showMessageDialog(null, "Database is created");
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Add problem");
			}
		} else if (e.getSource() == clear) {
			fnTf.setText("");
			inTf.setText("");
			pTf.setText("");
			gTf.setText("");

		}

		else if (e.getSource() == delete) {

			int num = table.getSelectedRow();

			if (num >= 0) {
				try {
					Connection con = DBConnection.getConnection();
					Statement st = con.createStatement();
					st.executeUpdate("delete from students where firstname = ('" + fnTf.getText() + "')");
					JOptionPane.showMessageDialog(null, "Record is deleted");

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Delete problem");
				}
			} else {
				JOptionPane.showMessageDialog(null, "You haven't selected anything");
			}

		}

		else if (e.getSource() == update) {
			first = fnTf.getText();
			last = inTf.getText();
			phone = pTf.getText();
			cgpa = gTf.getText();
			try {
				Connection con = DBConnection.getConnection();
				Statement st = con.createStatement();
				st.executeUpdate("update students set firstname = ('" + fnTf.getText() + "'), lastname = ('"
						+ inTf.getText() + "'), phone = ('" + pTf.getText() + "'), cgpa = ('" + gTf.getText()
						+ "') where firstname = ('" + fnTf.getText() + "')");
				JOptionPane.showMessageDialog(null, "Update successfull");

			} catch (SQLException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, "Update unsuccessfull");
			}
		}
		Database();
	}
}
