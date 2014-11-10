package javax.microedition.pim;

import java.util.Enumeration;
import gnu.testlet.TestHarness;
import gnu.testlet.Testlet;

public class TestPIM implements Testlet {
    public void test(TestHarness th) {
        try {
            th.check(System.getProperty("microedition.pim.version") != null);

            PIM pimInst = PIM.getInstance();

            th.check(pimInst.listPIMLists(PIM.CONTACT_LIST) != null);

            ContactList contactList = (ContactList)pimInst.openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY);

            Contact contact = contactList.createContact();
            th.check(contactList.isSupportedField(Contact.UID));
            th.check(contactList.isSupportedAttribute(Contact.UID, Contact.ATTR_NONE));
            contact.addString(Contact.UID, Contact.ATTR_NONE, "2");

            Enumeration contacts = contactList.items((PIMItem)contact);
            th.check(contacts.hasMoreElements());
            Contact foundContact = (Contact)contacts.nextElement();
            String tel = foundContact.getString(Contact.TEL, Contact.ATTR_NONE);

            th.check(tel, "+16505550102");

            th.check(!contacts.hasMoreElements());

            contactList.close();
        } catch (PIMException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
        }
    }
}
