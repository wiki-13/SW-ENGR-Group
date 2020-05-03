import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
/*
public class Patient {
    public String patientid;
    public String age;
    public String menopause;
    public String tumor_size;
    public String node_caps;
    public String breast;
    public String breast_quad;
    public String irradiat;
    public String class_type;
    public String requestID;
    public String senderIPAddress;
    public String receiverIPAddress;

    public static Patient createNewPatient(String patientid, String age, String menopause,
    String tumor_size,String node_caps, String breast, String breast_quad, String irradiat,
    String class_type, String requestID,String senderIPAddress, String receiverIPAddress) {
        Patient patient = new Patient();
        patient.patientid = patientid;
        patient.age = age;
        patient.menopause = menopause;
        patient.tumor_size = tumor_size;
        patient.node_caps = node_caps;
        patient.breast = breast;
        patient.breast_quad = breast_quad;
        patient.irradiat = irradiat;
        patient.class_type = class_type;
        patient.requestID = requestID;
        patient.senderIPAddress = senderIPAddress;
        patient.receiverIPAddress = receiverIPAddress;
        return patient;
    }
}
*/
public class Patient {
    public String patientid;
    public String age;
    public String menopause;
    public String tumor_size;
    public String inv_nodes;
    public String node_caps;
    public String deg_malig;
    public String breast;
    public String breast_quad;
    public String irradiat;
    public String class_type;
    public String requestID;
    public String senderIPAddress;
    public String receiverIPAddress;

    public  Patient (String pid, String a, String m, String ts, String in, String nc, String dm,
                     String b, String bq, String i, String ct, String rid,String sip, String rip) {

        patientid = pid;
        age = a;
        menopause = m;
        tumor_size = ts;
        inv_nodes = in;
        node_caps = nc;
        deg_malig = dm;
        breast = b;
        breast_quad = bq;
        irradiat = i;
        class_type = ct;
        requestID = rid;
        senderIPAddress = sip;
        receiverIPAddress = rip;
    }
}