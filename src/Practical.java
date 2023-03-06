import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

abstract class Shape {
	private String name;

	public Shape(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract double getArea();

	@Override
	public int hashCode() {
		return Double.valueOf(getArea()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Shape other = (Shape) obj;
		return getArea() == other.getArea();
	}
}

class Circle extends Shape {
	private double radius;

	public Circle(String name, double radius) {
		super(name);
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	@Override
	public double getArea() {
		return Math.PI * radius * radius;
	}
}

class Rectangle extends Shape {
	private double height;
	private double width;

	public Rectangle(String name, double height, double width) {
		super(name);
		this.height = height;
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	@Override
	public double getArea() {
		return height * width;
	}
}

class Square {
	protected double width;

	public Square(double width) {
		this.width = width;
	}

	public double getPerimeter() {
		return width * 4;
	}
}

class Rectang extends Square {
	private double height;

	public Rectang(double width, double height) {
		super(width);
		this.height = height;
	}

	@Override
	public double getPerimeter() {
		return 2 * (width + height);
	}
}

class Employee {
	private String name;
	private int experience;
	private BigDecimal basePayment;

	public Employee(String name, int experience, BigDecimal basePayment) {
		this.name = name;
		this.experience = experience;
		this.basePayment = basePayment;
	}

	public String getName() {
		return name;
	}

	public int getExperience() {
		return experience;
	}

	public BigDecimal getPayment() {
		return basePayment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, experience, basePayment);
	}

	@Override
	public boolean equals(final Object o) {
		Employee employee = (Employee) o;
		return experience == employee.experience && name == employee.name && basePayment.equals(employee.basePayment);
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", experience=" + experience + ", basePayment=" + basePayment + "]";
	}
}

class Manager extends Employee {
	private double coefficient;

	public Manager(String name, int experience, BigDecimal basePayment, double coefficient) {
		super(name, experience, basePayment);
		this.coefficient = coefficient;
	}

	public double getCoefficient() {
		return coefficient;
	}

	@Override
	public BigDecimal getPayment() {
		return super.getPayment().multiply(new BigDecimal(coefficient));
	}

	@Override
	public String toString() {
		return "Manager" + super.toString().substring(8, super.toString().length() - 1)
				.concat(", coefficient=" + coefficient + "]");
	}
}

class MyUtils {
	public static class Student {
		private int id;
		private String name;

		public Student(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public int hashCode() {
			return name == null ? id : id + name.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			Student student = (Student) o;
			return (student.hashCode() == hashCode()) ? true : false;
		}

		@Override
		public String toString() {
			return "Students [id=" + id + ", name=" + name + "]";
		}
	}

	public List<Shape> maxAreas(List<Shape> shapes) {
		List<Shape> list = new ArrayList<>();

		int indexCircle = 0;
		double maxCircle = 0;

		int indexRectangle = 0;
		double maxRectangle = 0;

		for (int i = 0; i < shapes.size(); i++) {
			if (shapes.get(i) instanceof Circle && shapes.get(i).getArea() > maxCircle) {
				maxCircle = shapes.get(i).getArea();
				indexCircle = i;
			}
			if (shapes.get(i) instanceof Rectangle && shapes.get(i).getArea() > maxRectangle) {
				maxRectangle = shapes.get(i).getArea();
				indexRectangle = i;
			}
		}

		if (!shapes.isEmpty()) {
			list.add(shapes.get(indexCircle));
			list.add(shapes.get(indexRectangle));
		}

		return list;
	}

	public double sumPerimeter(List<Square> firures) {
		double sum = 0;
		for (Square firure : firures) {
			if (firure == null)
				continue;
			sum += firure.getPerimeter();
		}
		return sum;
	}

	public Map<String, List<String>> createNotebook(Map<String, String> phones) {
		Map<String, List<String>> map = new HashMap<>();
		for (String key : phones.values().stream().distinct().collect(Collectors.toList())) {
			List<String> value = new ArrayList<>();
			for (Map.Entry<String, String> pair : phones.entrySet()) {
				if (pair.getValue() == null || pair.getValue().equals(key)) {
					value.add(pair.getKey());
				}
			}
			map.put(key, value);
		}
		return map;
	}

	public Set<Student> commonStudents(List<Student> list1, List<Student> list2) {
		Set<Student> set = new HashSet<>();
		for (Student student : list1) {
			if (list2.contains(student)) {
				set.add(student);
			}
		}
		return set;
	}

	private List<Employee> maxExperience(List<Employee> employees) {
		List<Employee> result = new ArrayList<>();
		employees.forEach(o -> {
			if (o.getExperience() == Collections.max(employees, Comparator.comparing(obj -> obj.getExperience()))
					.getExperience()) {
				result.add(o);
			}
		});
		return result;
	}

	private List<Employee> maxPayment(List<Employee> employees) {
		List<Employee> result = new ArrayList<>();
		employees.forEach(o -> {
			if (o.getPayment() == Collections.max(employees, Comparator.comparing(obj -> obj.getPayment()))
					.getPayment()) {
				result.add(o);
			}
		});
		return result;
	}

	public List<Employee> largestEmployees(List<Employee> workers) {
		List<Employee> list = new ArrayList<>();
		List<Employee> managersList = new ArrayList<>();
		List<Employee> employeesList = new ArrayList<>();

		for (Employee employee : workers) {
			if (employee instanceof Manager) {
				if (!managersList.contains(employee)) {
					managersList.add(employee);
				}
			} else if (employee instanceof Employee) {
				if (!employeesList.contains(employee)) {
					employeesList.add(employee);
				}
			}
		}

		maxExperience(employeesList).forEach(o -> {
			if (!list.contains(o))
				list.add(o);
		});
		maxExperience(managersList).forEach(o -> {
			if (!list.contains(o))
				list.add(o);
		});
		maxPayment(employeesList).forEach(o -> {
			if (!list.contains(o))
				list.add(o);
		});
		maxPayment(managersList).forEach(o -> {
			if (!list.contains(o))
				list.add(o);
		});

		return list;
	}

	public boolean verifyBrackets(String text) {
		Stack<Character> stack = new Stack<Character>();
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\\') {
				count++;
				continue;
			}
			if (count == 1) {
				if (++count == 2)
					count = 0;
				continue;
			}

			if ((text.charAt(i) == '(') || (text.charAt(i) == '[') || (text.charAt(i) == '{')) {
				stack.push(text.charAt(i));
			} else {
				if (stack.isEmpty() || (text.charAt(i) == ')') && (stack.peek() != '(')
						|| (text.charAt(i) == ')') && (stack.peek() != '(')
						|| (text.charAt(i) == ']') && (stack.peek() != '[')
						|| (text.charAt(i) == '}') && (stack.peek() != '{')) {
					return false;
				}
				stack.pop();
			}
		}
		return stack.isEmpty();
	}

	public String reformatLines(String text) {
		final int MAX_LINE_LENGTH = 60;
		StringBuilder line = new StringBuilder(MAX_LINE_LENGTH);
		StringBuilder res = new StringBuilder();

		for (String word : text.trim().split("\\s+")) {
			if (line.length() + word.length() > MAX_LINE_LENGTH) {
				res.append(line.substring(0, line.length() - 1)).append("\n");
				line.setLength(0);
			}
			line.append(word).append(" ");
		}
		return res.append(line.substring(0, line.length() - 1)).toString();
	}
}

public class Practical {
	public static int sumOfDigits(int number) {
		return number == 0 ? 0 : number % 10 + sumOfDigits(number / 10);
	}

	public static boolean isLeapYear(int year) {
		return year % 4 == 0 ? year % 100 == 0 ? year % 400 == 0 ? true : false : true : false;
	}

	public static void main(String[] args) {
		System.out.println("Java Fundamental Practical. Q1 is " + (Practical_1_1() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Java Fundamental Practical. Q2 is " + (Practical_1_2() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Class Design. Polymorphism. Q1 is " + (Practical_2_1() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Class Design. Polymorphism. Q2 is " + (Practical_2_2() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Array. Collections. Polymorphism. Q1 is " + (Practical_3_1() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Array. Collections. Polymorphism. Q2 is " + (Practical_3_2() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("Array. Collections. Polymorphism. Q3 is " + (Practical_3_3() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("String. StringBuilder. Practical Q1 is " + (Practical_4_1() ? "SUCCESSFUL" : "FAILED"));
		System.out.println("String. StringBuilder. Practical Q2 is " + (Practical_4_2() ? "SUCCESSFUL" : "FAILED"));
	}

	/*
	 * Java Fundamental Practical. Question 1. Write a method to compute the sum of
	 * the digits in a three-digits positive integer number.
	 */
	public static boolean Practical_1_1() {
		return (sumOfDigits(123) == 6 || sumOfDigits(608) == 14 || sumOfDigits(380) == 11 || sumOfDigits(111) == 3
				|| sumOfDigits(999) == 27) ? true : false;
	}

	/*
	 * Java Fundamental Practical. Question 2. Write a method to check if a year is
	 * a leap year or not, using only Relational and Logical operators. If a year is
	 * leap then method should return true, otherwise - false. Every year that is
	 * exactly divisible by 4 is a leap year, except for years that are exactly
	 * divisible by 100, but these centurial years are leap years if they are
	 * exactly divisible by 400.
	 */
	public static boolean Practical_1_2() {
		return (isLeapYear(1952) == true || isLeapYear(1974) == false || isLeapYear(2020) == true
				|| isLeapYear(2000) == true || isLeapYear(1900) == false) ? true : false;
	}

	/*
	 * Class Design. Polymorphism. Practical. Question 1. Please create class Shape
	 * with abstract method to calculate area of figure and field name. Replace code
	 * in method getArea() according to principles of polymorphism i.e. Circle and
	 * Rectangle classes extends Shape class and override double getArea() method.
	 * Develop maxAreas() method of the MyUtils class to return a List with
	 * instances of maximum area. The original list must be unchanged. For example,
	 * for a given list [Circle [radius=2.00], Rectangle [height=2.00, width=3.00],
	 * Circle [radius=1.00], Rectangle [height=3.00, width=2.00], Circle
	 * [radius=0.50], Rectangle [height=1.00, width=2.00]] you should get [Circle
	 * [radius=2.00], Rectangle [height=2.00, width=3.00], Rectangle [height=3.00,
	 * width=2.00]]
	 */
	public static boolean Practical_2_1() {
		Shape object1 = new Circle("Circle", 2);
		Shape object2 = new Rectangle("Rectangle", 2, 3);
		Shape object3 = new Circle("Circle", 2);
		Shape object4 = new Rectangle("Rectangle", 2, 3);
		Shape object5 = new Circle("Circle", 2);
		Shape object6 = new Rectangle("Rectangle", 3, 2);

		List<Shape> shapes = new LinkedList<Shape>();
		shapes.add(object1);
		shapes.add(object2);
		shapes.add(object3);
		shapes.add(object4);
		shapes.add(object5);
		shapes.add(object6);
		shapes.add(null);

		MyUtils utils = new MyUtils();
		utils.maxAreas(shapes);
		shapes.isEmpty();

		boolean result = false;

		try {
			result = utils.maxAreas(shapes).get(0) == object1 && utils.maxAreas(shapes).get(1) == object2;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	/*
	 * Class Design. Polymorphism. Practical. Question 2. Create classes Square and
	 * Rectang with method (double getPerimeter()) for calculating of perimeter.
	 * Find solution for avoiding of duplicate code. Create a double
	 * sumPerimeter(List<?> firures) method of the MyUtils class to return a sum
	 * perimeters of all figures. For example, for a given list [[Square
	 * [width=4.00], Square [width=5.00], Rectang [height=2.00, width=3.00]] you
	 * should get 46
	 */
	public static boolean Practical_2_2() {
		ArrayList<Square> list = new ArrayList<Square>();
		list.add(new Square(4));
		list.add(new Square(5));
		list.add(new Rectang(2, 3));
		list.add(null);

		MyUtils utils = new MyUtils();
		return utils.sumPerimeter(list) == 46.0 ? true : false;
	}

	/*
	 * Array. Collections. Polymorphism. Question 1. Create a createNotebook()
	 * method of the MyUtils class to create a new map with name as key and phone
	 * list as value. The method receives a map with phone as key and name as value.
	 * For example, for a given map {0967654321=Petro, 0677654321=Petro,
	 * 0501234567=Ivan, 0970011223=Stepan, 0631234567=Ivan } you should get
	 * {Ivan=[0501234567, 0631234567], Petro=[0967654321, 0677654321],
	 * Stepan=[0970011223]}
	 */
	public static boolean Practical_3_1() {
		Map<String, String> phones = new HashMap<>();
		phones.put("0967654321", "Petro");
		phones.put("0677654321", "Petro");
		phones.put("0501234567", "Ivan");
		phones.put("0970011223", "Stepan");
		phones.put("0631234567", "Ivan");

		String result = "";

		MyUtils utils = new MyUtils();
		Map<String, List<String>> resultMap = utils.createNotebook(phones);
		result += "{";
		for (Map.Entry<String, List<String>> pair : resultMap.entrySet()) {
			result += pair.getKey() + "=[";
			for (String value : pair.getValue()) {
				result += value + ", ";
			}
			result = result.substring(0, result.length() - 2) + "], ";
		}
		result = result.substring(0, result.length() - 2) + "}";

		return result.equals("{Ivan=[0501234567, 0631234567], Petro=[0967654321, 0677654321], Stepan=[0970011223]}")
				? true
				: false;
	}

	/*
	 * Array. Collections. Polymorphism. Question 2. Create the commonStudents()
	 * method of the MyUtils class to return a HashSet of common elements of two
	 * student lists. For example, for a given list1 [Students [id=1, name=Ivan],
	 * Students [id=2, name=Petro], Students [id=3, name=Stepan]] and list2
	 * [Students [id=1, name=Ivan], Students [id=3, name=Stepan], Students [id=4,
	 * name=Andriy]] you should get [Students [id=3, name=Stepan], Students [id=1,
	 * name=Ivan]]
	 */
	public static boolean Practical_3_2() {
		List<MyUtils.Student> list1 = new ArrayList<>();
		list1.add(new MyUtils.Student(1, "Ivan"));
		list1.add(new MyUtils.Student(2, "Petro"));
		list1.add(new MyUtils.Student(3, "Stepan"));
		// System.out.println(list1.toString());

		List<MyUtils.Student> list2 = new ArrayList<>();
		list2.add(new MyUtils.Student(1, "Ivan"));
		list2.add(new MyUtils.Student(3, "Stepan"));
		list2.add(new MyUtils.Student(4, "Andriy"));

		String result = "[";
		for (MyUtils.Student student : new MyUtils().commonStudents(list1, list2)) {
			result += student + ", ";
		}
		result = result.substring(0, result.length() - 2) + "]";

		return result.equals("[Students [id=3, name=Stepan], Students [id=1, name=Ivan]]") ? true : false;
	}

	/*
	 * Array. Collections. Polymorphism. Question 3. Create classes Employee (fields
	 * String name, int experience and BigDecimal basePayment) and Manager (field
	 * double coefficient) with methods which return the general working experience
	 * and payment for work done. A getter methods of class Employee return value of
	 * all fields: getName(), getExperience() and getPayment(). Classes Manager is
	 * derived from class Employee and override getPayment() method to return
	 * multiplication of a coefficient and base payment. Create a largestEmployees()
	 * method of the MyUtils class to return a List of unique employees with maximal
	 * working experience or payment without duplicate objects. The original list
	 * must be unchanged. For example, for a given list [Employee [name=Ivan,
	 * experience=10, basePayment=3000.00], Manager [name=Petro, experience=9,
	 * basePayment=3000.00, coefficient=1.5], Employee [name=Stepan, experience=8,
	 * basePayment=4000.00], Employee [name=Andriy, experience=7,
	 * basePayment=3500.00], Employee [name=Ihor, experience=5,
	 * basePayment=4500.00], Manager [name=Vasyl, experience=8, basePayment=2000.00,
	 * coefficient=2.0]] you should get [Employee [name=Ivan, experience=10,
	 * basePayment=3000.00], Manager [name=Petro, experience=9, basePayment=3000.00,
	 * coefficient=1.5], Employee [name=Ihor, experience=5, basePayment=4500.00]].
	 */
	public static boolean Practical_3_3() {
		List<Employee> workers = new ArrayList<>();
		workers.add(new Employee("Ivan", 10, new BigDecimal(3000.00)));
		workers.add(new Manager("Petro", 9, new BigDecimal(3000.00), 1.5));
		workers.add(new Employee("Stepan", 8, new BigDecimal(4000.00)));
		workers.add(new Employee("Andriy", 7, new BigDecimal(3500.00)));
		workers.add(new Employee("Ihor", 5, new BigDecimal(4500.00)));
		workers.add(new Manager("Vasyl", 8, new BigDecimal(2000.00), 2.0));

		String result = "[";
		for (Employee employee : new MyUtils().largestEmployees(workers)) {
			result += employee + ", ";
		}
		result = result.substring(0, result.length() - 2) + "]";

		return result.equals(
				"[Employee [name=Ivan, experience=10, basePayment=3000], Manager [name=Petro, experience=9, basePayment=3000, coefficient=1.5], Employee [name=Ihor, experience=5, basePayment=4500]]")
						? true
						: false;
	}

	/*
	 * String. StringBuilder. Practical. Question 1. Given a text containing
	 * brackets '(', ')', '{', '}', '[' and ']'. Sequences "\\(", "\\)", "\\[",
	 * "\\]", "\\{" and "\\}" are not brackets. Write a boolean
	 * verifyBrackets(String text) method of the MyUtils class to check a input
	 * text. The brackets must close in the correct order, for example "()",
	 * "()[]{}", "{(())}" and "(\\()" are all valid but "(]", ")(" and "([)]" are
	 * not.
	 */
	public static boolean Practical_4_1() {
		return new MyUtils().verifyBrackets("[\\]()]\\)()");
	}

	/*
	 * String. StringBuilder. Practical. Question 2. Write a String
	 * reformatLines(String text) method of the MyUtils class to replacing all
	 * spaces with one and reformat lines of input text. Length of every lines
	 * should not exceed 60 characters. For example, for a given text Java was
	 * originally developed by James Gosling at Sun Microsystems (which has since
	 * been acquired by Oracle) and released in 1995 as a core component of Sun
	 * Microsystems' Java platform. you should get Java was originally developed by
	 * James Gosling at Sun Microsystems (which has since been acquired by Oracle)
	 * and released in 1995 as a core component of Sun Microsystems' Java platform.
	 */
	public static boolean Practical_4_2() {
		String in = "   Java    was      originally developed\n"
				+ "   by    James   Gosling at Sun Microsystems (which\n" + " has since been\n"
				+ "acquired by Oracle) and released in 1995\n"
				+ " as a core component of Sun Microsystems' Java platform.";
		String out = "Java was originally developed by James Gosling at Sun\n"
				+ "Microsystems (which has since been acquired by Oracle) and\n"
				+ "released in 1995 as a core component of Sun Microsystems'\n" + "Java platform.";
		return new MyUtils().reformatLines(in).equals(out);
	}
}