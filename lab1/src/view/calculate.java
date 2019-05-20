package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class calculate extends JFrame {

	private CalcFunc Func=null;
	private JPanel contentPane;
	private JTextField jTextField;
	private double op1=0,op2=0;

	public double PI=3.141592655358989323846264;
	public int m_Left=0;
	public int m_Bits=0;
	public double m_Operand=0;
	public double m_Accum=0;
	public boolean m_Operable=false;
	public boolean m_Dot=false;
	public String Str;
	
	Stack<Double> m_Stack=null;
	Stack<Operator> o_Stack=null;
	
	private enum Operator { OpNone,OpLeft,OpAdd,OpSub,OpMul,OpDiv,OpRight};
	public Operator m_Operator=Operator.OpNone;
	private JTextField jLeft;
	private JTextField textM;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					calculate frame = new calculate();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public calculate() {
		setTitle("科学计算器@ChenHsing");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 494, 472);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * 文本域，即为计算器的屏幕显示区域
		 */
		jTextField = new JTextField();
		jTextField.setFont(new Font("Digital-7", Font.PLAIN, 40));
		jTextField.setBounds(6, 29, 446, 51);
		contentPane.add(jTextField);
		jTextField.setColumns(10);

		jTextField.setEditable(false);//文本区域不可编辑
		jTextField.setBackground(Color.white);//文本区域的背景色
		jTextField.setHorizontalAlignment(JTextField.RIGHT);//文字右对齐
		jTextField.setText("0");
		

		jLeft = new JTextField();
		jLeft.setBounds(390, 93, 62, 24);
		contentPane.add(jLeft);
		jLeft.setColumns(10);
		
		JLabel jLabel = new JLabel("New label");
		jLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		jLabel.setBounds(177, 0, 197, 27);
		contentPane.add(jLabel);
		
		
		textM = new JTextField();
		textM.setBounds(6, 29, 28, 24);
		contentPane.add(textM);
		textM.setColumns(10);
		
		

		Func=new CalcFunc(jLabel,this.jTextField,this.jLeft,textM);
		
		JButton Buttonsign = new JButton("±");
		Buttonsign.setBounds(84, 340, 62, 27);
		contentPane.add(Buttonsign);
		
		JButton Buttonpoint = new JButton(".");
		Buttonpoint.setBounds(236, 340, 62, 27);
		contentPane.add(Buttonpoint);
		
		JButton Button0 = new JButton("0");
		Button0.setBounds(160, 340, 62, 27);
		contentPane.add(Button0);
	
		JButton Button1 = new JButton("1");
		Button1.setBounds(84, 300, 62, 27);
		contentPane.add(Button1);
		
		JButton Button2 = new JButton("2");
		Button2.setBounds(160, 300, 62, 27);
		contentPane.add(Button2);
		
		JButton Button3 = new JButton("3");
		Button3.setBounds(236, 300, 62, 27);
		contentPane.add(Button3);
		
		JButton Button4 = new JButton("4");
		Button4.setBounds(84, 254, 62, 27);
		contentPane.add(Button4);
		
		JButton Button5 = new JButton("5");
		Button5.setBounds(160, 254, 62, 27);
		contentPane.add(Button5);
		
		JButton Button6 = new JButton("6");
		Button6.setBounds(236, 254, 62, 27);
		contentPane.add(Button6);
		
		JButton Button7 = new JButton("7");
		Button7.setBounds(84, 211, 62, 27);
		contentPane.add(Button7);
		
		JButton Button8 = new JButton("8");
		Button8.setBounds(160, 211, 62, 27);
		contentPane.add(Button8);
		
		JButton Button9 = new JButton("9");
		Button9.setBounds(236, 211, 62, 27);
		contentPane.add(Button9);
		
		JButton Buttonjia = new JButton("+");
		Buttonjia.setBounds(312, 340, 62, 27);
		contentPane.add(Buttonjia);
		
		JButton Buttonjian = new JButton("-");
		Buttonjian.setBounds(312, 300, 62, 27);
		contentPane.add(Buttonjian);
		
		JButton Buttonchen = new JButton("×");
		Buttonchen.setBounds(312, 254, 62, 27);
		contentPane.add(Buttonchen);
		
		JButton Buttonchu = new JButton("÷");
		Buttonchu.setBounds(312, 211, 62, 27);
		contentPane.add(Buttonchu);
		
		JButton Buttonsin = new JButton("sin");
		Buttonsin.setBounds(84, 171, 62, 27);
		contentPane.add(Buttonsin);
		
		JButton Buttoncos = new JButton("cos");
		Buttoncos.setBounds(160, 171, 62, 27);
		contentPane.add(Buttoncos);
		
		JButton Buttontan = new JButton("tan");
		Buttontan.setBounds(236, 171, 62, 27);
		contentPane.add(Buttontan);
		
		JButton Buttondelete = new JButton("←");
		Buttondelete.setBounds(312, 131, 64, 27);
		contentPane.add(Buttondelete);
		
		JButton ButtonC = new JButton("C");
		ButtonC.setBounds(388, 131, 64, 27);
		contentPane.add(ButtonC);
		
		JButton Buttonx2 = new JButton("x²");
		Buttonx2.setBounds(388, 211, 62, 27);
		contentPane.add(Buttonx2);
		
		JButton Buttonxy = new JButton("x^y");
		Buttonxy.setBounds(388, 254, 62, 27);
		contentPane.add(Buttonxy);
		
		JButton Buttonans = new JButton("=");
		Buttonans.setBounds(388, 300, 62, 67);
		contentPane.add(Buttonans);
		
		JButton Buttonlog = new JButton("log");
		Buttonlog.setBounds(84, 131, 62, 27);
		contentPane.add(Buttonlog);
		
		JButton Buttonexp = new JButton("Exp");
		Buttonexp.setBounds(160, 131, 62, 27);
		contentPane.add(Buttonexp);
		
		JButton Buttonmod = new JButton("mod");
		Buttonmod.setBounds(236, 131, 62, 27);
		contentPane.add(Buttonmod);
		
		JButton Buttonn = new JButton("n!");
		Buttonn.setBounds(312, 171, 62, 27);
		contentPane.add(Buttonn);
		
		JButton Buttonsqr = new JButton("√");
		Buttonsqr.setBounds(388, 171, 62, 27);
		contentPane.add(Buttonsqr);
		
		JButton ButtonMadd = new JButton("M+");
		ButtonMadd.setBounds(236, 93, 64, 27);
		contentPane.add(ButtonMadd);
		
		JButton ButtonMjian = new JButton("M-");
		ButtonMjian.setBounds(312, 93, 64, 27);
		contentPane.add(ButtonMjian);
		
		JButton Buttonleft = new JButton("(");
		Buttonleft.setBounds(6, 300, 64, 27);
		contentPane.add(Buttonleft);
		
		JButton Buttonright = new JButton(")");
		Buttonright.setBounds(6, 340, 64, 27);
		contentPane.add(Buttonright);
		
		JButton Buttonpai = new JButton("π");
		Buttonpai.setBounds(6, 211, 62, 27);
		contentPane.add(Buttonpai);
		
		JButton Buttone = new JButton("e");
		Buttone.setBounds(6, 254, 62, 27);
		contentPane.add(Buttone);
		
		JButton ButtonMS = new JButton("MS");
		ButtonMS.setBounds(158, 92, 64, 27);
		contentPane.add(ButtonMS);
		
		JButton ButtonMR = new JButton("MR");
		ButtonMR.setBounds(84, 93, 62, 27);
		contentPane.add(ButtonMR);
		
		JButton ButtonMC = new JButton("MC");
		ButtonMC.setBounds(6, 93, 62, 27);
		contentPane.add(ButtonMC);
		
		JButton Buttondaoshu = new JButton("1/x");
		Buttondaoshu.setBounds(8, 131, 62, 27);
		contentPane.add(Buttondaoshu);
		
		JButton Buttonx3 = new JButton("x³");
		Buttonx3.setBounds(6, 171, 64, 27);
		contentPane.add(Buttonx3);

	
		//为记忆功能按钮增加监听事件
		ButtonMS.addActionListener(new Action());
		ButtonMR.addActionListener(new Action());
		ButtonMC.addActionListener(new Action());
		ButtonMadd.addActionListener(new Action());
		ButtonMjian.addActionListener(new Action());
		
		//为数字按钮增加监听事件
		Button0.addActionListener(new Action());
		Button1.addActionListener(new Action());
		Button2.addActionListener(new Action());
		Button3.addActionListener(new Action());
		Button4.addActionListener(new Action());
		Button5.addActionListener(new Action());
		Button6.addActionListener(new Action());
		Button7.addActionListener(new Action());
		Button8.addActionListener(new Action());
		Button9.addActionListener(new Action());
		Buttonpai.addActionListener(new Action());
		Buttone.addActionListener(new Action());
		
		//为归零和退格键以及符号键设置监听
		Buttondelete.addActionListener(new Action());
		ButtonC.addActionListener(new Action());
		Buttonsign.addActionListener(new Action());
		
		//为运算符设置监听事件
		Buttonjia.addActionListener(new Action());
		Buttonjian.addActionListener(new Action());
		Buttonchen.addActionListener(new Action());
		Buttonchu.addActionListener(new Action());
		Buttonsin.addActionListener(new Action());
		Buttoncos.addActionListener(new Action());
		Buttontan.addActionListener(new Action());
		Buttonlog.addActionListener(new Action());
		Buttonx2.addActionListener(new Action());
		Buttonx3.addActionListener(new Action());
		Buttonxy.addActionListener(new Action());
		Buttonn.addActionListener(new Action());
		Buttonexp.addActionListener(new Action());
		Buttonmod.addActionListener(new Action());
		Buttonsqr.addActionListener(new Action());
		Buttonans.addActionListener(new Action());
		Buttonleft.addActionListener(new Action());
		Buttonright.addActionListener(new Action());
		Buttondaoshu.addActionListener(new Action());
		
		//为小数点设置监听事件
		Buttonpoint.addActionListener(new Action());
	}
	
	/**
	 * 清除按钮的事件监听
	 */
	class Action implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			/*
			 * 用ActionEvent对象的getActionCommand()方法
			 * 取得与引发事件对象相关的字符串
			 */
			String Command=e.getActionCommand();
			//this.jLabel.setText("输入...");
			if(Command.equals("sin"))
				Func.Sin();
			else if(Command.equals("cos"))
				Func.Cos();
			else if(Command.equals("tan"))
				Func.Tan();
			else if(Command.equals("log"))
				Func.Log();
			else if(Command.equals("x²"))
				Func.X2();
			else if(Command.equals("x³"))
				Func.X3();
			else if(Command.equals("n!"))
				Func.jiechen();
			else if(Command.equals("1/x"))
				Func.X1();
			else if(Command.equals("("))
				Func.Left();
			else if(Command.equals(")"))
				Func.Right();
			else if(Command.equals("√"))
				Func.Sqrt();
			else if(Command.equals("C"))
				Func.Clear();
			else if(Command.equals("←"))
				Func.Delete();
				//System.exit(0);
			else if(Command.equals("="))
				Func.Be();
			else if(Command.equals("±"))
				Func.As();
			else if(Command.equals("MS"))
				Func.MS();
			else if(Command.equals("MR"))
				Func.MR();
			else if(Command.equals("MC"))
				Func.MC();
			else if(Command.equals("M+"))
				Func.Madd();
			else if(Command.equals("M-"))
				Func.Mminus();
			else
				Func.Input(Command);
		}
	}
}
