package laplab.student;

/**
 * Created with IntelliJ IDEA.
 * User: ahmed
 * Date: 2/26/14
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class StudentInfo {
    boolean empty = false;
    private int id = 0;
    private int room = 0;
    private int batch = 0;
    private int dept = 0;
    private String name;
    private String contact;
    private String department;
    private String parent_name;
    private String parent_contact;
    private String blood_group;

    public StudentInfo() {

    }

    public StudentInfo(int _id, int _room, String _name, String _contact, String _parent_name, String _parent_contact, String _blood_group) {
        id = _id;
        room = _room;
        name = _name;
        contact = _contact;
        parent_name = _parent_name;
        parent_contact = _parent_contact;
        blood_group = _blood_group;

    }

    public StudentInfo(int _id, int _room, String _name, String _contact) {
        id = _id;
        room = _room;
        name = _name;
        contact = _contact;
        parent_name = null;
        parent_contact = null;
        blood_group = null;

    }

    public StudentInfo(int _id, int _room, String _name, String _contact, String _parent_name) {
        id = _id;
        room = _room;
        name = _name;
        contact = _contact;
        parent_name = _parent_name;
        parent_contact = null;
        blood_group = null;

    }

    public StudentInfo(int _id, int _room, String _name, String _contact, String _parent_name, String _parent_contact) {
        id = _id;
        room = _room;
        name = _name;
        contact = _contact;
        parent_name = _parent_name;
        parent_contact = _parent_contact;
        blood_group = null;

    }

    private void calculate_Batch_Dept() {
        setBatch(id / 100000);
        setDept((id % 100000) / 1000);
    }

    private void calculate_Department_id(String _department) {
        if (_department == "CE") {
            setDept(1);
        } else if (_department == "EEE") {
            setDept(2);
        } else if (_department == "ME") {
            setDept(3);
        } else if (_department == "CSE") {
            setDept(4);
        } else if (_department == "URP") {
            setDept(5);
        } else if (_department == "Arch.") {
            setDept(6);
        } else if (_department == "PME") {
            setDept(7);
        } else if (_department == "ETE") {
            setDept(8);
        } else {
            System.out.println("Sorry Department not Found");
        }
    }

    private void calculate_Department(int _department) {
        if (_department == 1) {
            department = "CE";
        } else if (_department == 2) {
            department = "EEE";
        } else if (_department == 3) {
            department = "ME";
        } else if (_department == 4) {
            department = "CSE";
        } else if (_department == 5) {
            department = "URP";
        } else if (_department == 6) {
            department = "Arch.";
        } else if (_department == 7) {
            department = "PME";
        } else if (_department == 8) {
            department = "ETE";
        } else {
            System.out.println("Sorry Department not Found");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
        calculate_Batch_Dept();
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getParent_contact() {
        return parent_contact;
    }

    public void setParent_contact(String parent_contact) {
        this.parent_contact = parent_contact;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
        calculate_Batch_Dept();
        calculate_Department(dept);
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int _room) {
        room = _room;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getDept() {
        return dept;
    }

    public void setDept(int dept) {
        this.dept = dept;
        calculate_Department(dept);
    }

    public boolean isEmpty() {
        if (id == 0 || room == 0 || department == null || name == null) {
            empty = true;
        }
        return empty;
    }

    public boolean finalizeObject() {
        boolean ok = true;
        if (this.isEmpty()) {
            System.out.println("NO data found----");
            return false;
        }
        if (this.dept == 0) {
            calculate_Batch_Dept();
        }
        return ok;
    }

    public static int getDepartment(String studentId) {
        Integer id_ = Integer.getInteger(studentId);
        return ((id_ % 100000) / 1000);
    }

    public static int getBatch(String studentId) {
        Integer id_ = Integer.parseInt(studentId);
        return id_ / 100000;
    }

    public static int getDepartmentID(String _department) {
        if (_department.equals("CE")) {
            return 1;
        } else if (_department.equals("EEE")) {
            return 2;
        } else if (_department.equals("ME")) {
            return 3;
        } else if (_department.equals("CSE")) {
            return 4;
        } else if (_department.equals("URP")) {
            return 5;
        } else if (_department.equals("Arch")) {
            return 6;
        } else if (_department.equals("PME")) {
            return 7;
        } else if (_department.equals("ETE")) {
            return 8;
        } else {
            System.out.println("Sorry Department not Found");
            return 0;
        }
    }

}
