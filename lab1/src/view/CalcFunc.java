package view;

import java.text.DecimalFormat;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JTextField;
public class CalcFunc {
	private JLabel jLabel = null;
	private JTextField jTextField = null;
	private JTextField jLeft = null;
	private JTextField textM = null;
	private double op1=0,op2=0;

	public double memory=0;
	public int m_Left=0;
	public int m_Bits=0;
	public double m_Operand=0;
	public double m_Accum=0;
	public boolean m_Operable=false;
	public boolean m_Dot=false;
	public String Str;
	
	//定义栈结构来存储运算符和计算数
	Stack<Double> m_Stack=null;
	Stack<Operator> o_Stack=null;
	
	private enum Operator { OpNone,OpLeft,OpAdd,OpSub,OpFang,OpMod,OpMul,OpDiv,OpExp,OpRight};
	public Operator m_Operator=Operator.OpNone;
	
	//构造函数
	public CalcFunc(JLabel jL,JTextField jF,JTextField jTL,JTextField jM)
	{
		this.jLabel=jL;
		this.jTextField=jF;
		this.jLeft=jTL;
		this.textM = jM;
		m_Stack=new Stack<Double>();
		o_Stack=new Stack<Operator>();
		Clear();
	}
	
	/** 
	 * 判断double是否是整数 
	 * @param obj 
	 * @return 
	 */  
	public static boolean isIntegerForDouble(double obj) {  
	    double eps = 1e-10;  // 精度范围  
	    if(obj <1) {
	    	return false;
	    }
	    return obj-Math.floor(obj) < eps;  
	}

	public void Input(String str)
	{
		if(str.equals("."))
		{
			if(m_Dot) {
				this.m_Dot=true;
				this.m_Operable=true;
				return;
			}
			else {
				this.m_Dot=true;
				this.m_Operable=true;
				jTextField.setText(jTextField.getText()+str);
				return;
			}
			
		}
		if(str.equals("mod")) {
			this.m_Operable=false;
			this.m_Operator=Operator.OpMod;
			Run();
			return;
		}
		if(str.equals("Exp")) {
			this.m_Operable=false;
			this.m_Operator=Operator.OpExp;
			Run();
			return;
		}
		if(str.equals("x^y")) {
			this.m_Operable=false;
			this.m_Operator=Operator.OpFang;
			Run();
			return;
		}
		if(str.equals("+"))
		{
			this.m_Operable=false;
			this.m_Operator=Operator.OpAdd;			
			Run();
			return;
		}
		if(str.equals("-"))
		{
			this.m_Operable=false;
			this.m_Operator=Operator.OpSub;			
			Run();
			return;
		}
		if(str.equals("×"))
		{
			this.m_Operable=false;
			this.m_Operator=Operator.OpMul;			
			Run();
			return;
		}
		if(str.equals("÷")){
			this.m_Operable=false;
			this.m_Operator=Operator.OpDiv;			
			Run();
			return;
		}
		if(str.equals("π")) {
			this.m_Operand=Math.PI;
			this.m_Operable=true;
			Disp2();
			return;
		}
		else if(str.equals("e")) {
			this.m_Operand=Math.E;
			this.m_Operable=true;
			Disp2();
			return;
		}
		if(!this.m_Operable)
		{
			this.m_Operand=0;
			this.m_Dot=false;
			this.m_Bits=0;
		}
		if(this.m_Dot)
		{
			this.m_Bits++;
			this.m_Operand=this.m_Operand+Integer.parseInt(str)/Math.pow(10,this.m_Bits);
		}
		
		else {
			//System.out.println(m_Operand);
			this.m_Operand=this.m_Operand*10+Integer.parseInt(str);
			//System.out.println(m_Operand);
		}
			
		this.m_Operable=true;
		Disp();
	}
	
	public void Delete() {
		String str = jTextField.getText();
		System.out.println("hello"+str);
		
		if(Double.parseDouble(str) > 0){
			if(str.length() > 1){
				if((str.charAt(str.length()-1))=='.')
					this.m_Dot=true;
					
				m_Operand=Double.valueOf(str.substring(0, str.length()-1));
				//使用退格删除最后一位字符
				jTextField.setText(str.substring(0, str.length()-1));
			}else{
				jTextField.setText("0");
				m_Operand = 0;
			}
		}else{
			if(str.length() > 2){
				if((str.charAt(str.length()-1))=='.')
					this.m_Dot=true;
				jTextField.setText(str.substring(0,str.length() - 1));
				m_Operand=Double.valueOf(str.substring(0, str.length()-1));
			}else{
				jTextField.setText("0");
				m_Operand = 0;
			}
		}
	}
	
	public void Disp()
	{
		double lVal=(m_Operable) ? m_Operand:m_Accum;
		System.out.println(lVal);
		DecimalFormat df = new DecimalFormat("#.####");
		String temp = df.format(lVal);
		//System.out.println(temp);
		//Str=String.valueOf(lVal);
		Str=temp;
		if(Str.equals("Infinity"))
		{
			this.jLabel.setText("超过运算范围！");
			Str="0";
		}
		jTextField.setText(Str);
		m_Operand=Double.valueOf(Str);
	}
	
	public void Disp2()
	{
		double lVal=(m_Operable) ? m_Operand:m_Accum;
		System.out.println(lVal);
		Str=String.valueOf(lVal);

		if(Str.equals("Infinity"))
		{
			this.jLabel.setText("超过运算范围！");
			Str="0";
		}
		jTextField.setText(Str);
		m_Operand=Double.valueOf(Str);

	}
	
	
	public void Sqrt()
	{
		this.m_Operable=false;
		this.m_Operand=Math.sqrt(this.m_Operand);
		this.m_Accum=this.m_Operand;
		Disp();		
	}
	public void Sin()
	{
		this.m_Operand=this.m_Operand/180*Math.PI;
		this.m_Operand=Math.sin(this.m_Operand);
		this.m_Accum=this.m_Operand;
		this.m_Operable=false;
		Disp();
	}
	public void Cos()
	{
		this.m_Operand=this.m_Operand/180*Math.PI;
		this.m_Operand=Math.cos(this.m_Operand);
		this.m_Accum=this.m_Operand;
		this.m_Operable=false;
		Disp();		
	}
	public void Tan()
	{
		this.m_Operand=this.m_Operand/180*Math.PI;
		this.m_Operand=Math.tan(this.m_Operand);
		this.m_Accum=this.m_Operand;
		this.m_Operable=false;
		Disp();		
	}
	public void jiechen() {
		if(isIntegerForDouble(this.m_Operand)) {
			int n = (int) this.m_Operand;
			System.out.println(n);
			if(n>100)
				this.jLabel.setText("超过运算范围！");
			else
				this.m_Operand=fac(n);
			Disp();
		}
	}
	
	//递归计算阶乘
	public int fac(int number) {
	        if (number <= 1)
	            return 1;
	        else
	            return number * fac(number - 1);
	}
	
	public void Log()
	{
		this.m_Operand=Math.log(this.m_Operand);
		Disp();	
	}
	
	public void Left()
	{
		this.m_Left++;
		this.jLeft.setText("( "+String.valueOf(this.m_Left));
		this.m_Operator=Operator.OpLeft;
		this.m_Operable=false;
		Run();
	}
	public void Right()
	{
		if(this.m_Left!=0)
		{
			this.m_Operator=Operator.OpRight;
			this.m_Operable=false;
			this.m_Left--;
			if(this.m_Left!=0)
				this.jLeft.setText("( "+String.valueOf(this.m_Left));
			else
				this.jLeft.setText("");
			this.m_Stack.push(this.m_Operand);
			Operator Op;
			while(this.o_Stack.peek()!=Operator.OpLeft)
			{
				Op=this.o_Stack.pop();
				Compute(Op);
			}
			this.m_Operand=this.m_Accum=this.m_Stack.peek();
			Disp();
			this.o_Stack.pop();
			this.m_Stack.pop();
		}
	}
	public void X2()
	{
		this.m_Operable=false;
		this.m_Operand=this.m_Operand*this.m_Operand;
		this.m_Accum=this.m_Operand;
		Disp();
	}
	
	public void X3() {
		this.m_Operable=false;
		this.m_Operand=this.m_Operand*this.m_Operand*this.m_Operand;;
		this.m_Accum=this.m_Operand;
		Disp();
	}
	public void X1()
	{
		this.m_Operable=false;
		if(this.m_Operand==0)
		{
			this.jLabel.setText("除数不能为零！");
		}else
			this.m_Operand=1/this.m_Operand;
		this.m_Accum=this.m_Operand;
		Disp();		
	}
	
	//清空初始化
	public void Clear()
	{
		this.m_Accum=0;
		this.m_Left=0;
		this.m_Operable=false;
		this.m_Operand=0;
		this.Str="";
		this.jTextField.setText("0");
		this.m_Dot=false;
		this.m_Stack.clear();
		this.o_Stack.clear();
		this.jLeft.setText("");
		this.op1=0;
		this.op2=0;
		this.m_Operator=Operator.OpNone;
		this.jLabel.setText("科学计算器");
	}
	public void As()
	{
		this.m_Operable=false;
		this.m_Operand=this.m_Accum=(-1)*this.m_Operand;
		Disp();
	}
	public void Run()
	{
		Operator Go;
		if(this.o_Stack.empty()||(this.m_Operator==Operator.OpLeft)
				||this.m_Operator.ordinal()>this.o_Stack.peek().ordinal())
		{
			if(this.m_Operator==Operator.OpLeft)
				this.o_Stack.push(this.m_Operator);
			else
			{
				this.o_Stack.push(this.m_Operator);
				this.m_Stack.push(this.m_Operand);
			}
		}else
		{
			this.m_Stack.push(this.m_Operand);
			while(!this.o_Stack.empty() && this.m_Operator.ordinal()<=this.o_Stack.peek().ordinal())
			{
				Go=this.o_Stack.pop();
				Compute(Go);
			}
			this.o_Stack.push(this.m_Operator);
		}
		if(!this.m_Stack.empty())
		{
			this.m_Operand=this.m_Accum=this.m_Stack.peek();
			Disp();
		}
		if(this.m_Operator==Operator.OpNone)
		{
			this.o_Stack.clear();
			this.m_Stack.clear();
		}
	}
	public void Compute(Operator Opp)
	{
		int result;
		result=GetTwo();
		if(result==1)
		{
			System.out.println("op1"+op1+" op2"+op2);
			switch(Opp)
			{
				case OpAdd:	this.m_Stack.push(op1+op2);break;
				case OpMul:this.m_Stack.push(op1*op2);break;
				case OpFang:this.m_Stack.push(Math.pow(op2,op1));break;
				case OpMod:this.m_Stack.push(op2%op1);break;
				case OpSub:this.m_Stack.push(op2-op1);break;
				case OpDiv:
					if(op1==0)
						this.jLabel.setText("除数不能为零!");
					else
						this.m_Stack.push(op2/op1);
					break;
				case OpExp:
					this.m_Stack.push(op2*Math.pow(10, op1));
					break;
			default:
				break;
			}
		}else
		{
			this.m_Stack.clear();
			this.o_Stack.clear();
		}
		this.m_Operable=false;
	}
	private int GetTwo()
	{
		if(this.m_Stack.empty())	return 0;
			op1=this.m_Stack.pop();
		if(this.m_Stack.empty())	return 0;
			op2=this.m_Stack.pop();
		return 1;
		
	}
	public void Be()
	{
		this.m_Operable=false;
		this.m_Operator=Operator.OpNone;
		Run();
	}
	
	public void MS() {
		textM.setText("M");
		memory = Double.valueOf(jTextField.getText());
	}
	
	public void MC() {
		textM.setText("");
		memory = 0;
	}
	
	public void Madd() {
		memory+=Double.valueOf(jTextField.getText());
	}
	
	public void Mminus() {
		memory-=Double.valueOf(jTextField.getText());
	}
	
	public void MR() {
		jTextField.setText(String.valueOf(memory));
	}
}
