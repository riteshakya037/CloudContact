package com.cloudcontact.cloudcontact.BottomSheet;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudcontact.cloudcontact.Parse.ParseRow;
import com.cloudcontact.cloudcontact.R;
import com.parse.codec.binary.StringUtils;

import java.util.ArrayList;

/**
 * Created by rishakya on 7/7/15.
 */
public class BottomSheetAdaptor extends RecyclerView.Adapter<BottomSheetAdaptor.BottomSheetHolder> {
    private final LayoutInflater inflator;
    ArrayList<HashMapArray> bottomSheetList;
    Context context;

    BottomSheetAdaptor(final Context context, final ParseRow parseRow, TextView title, Button add_to_contact_btn) {
        this.context = context;
        inflator = LayoutInflater.from(context);
        bottomSheetList = new ArrayList<>();
        bottomSheetList = initializeMenu(parseRow);
        title.setText(parseRow.getName());
        add_to_contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String DisplayName = parseRow.getName();
                String MobileNumber = parseRow.getPhone_no()[0];
                String HomeNumber = parseRow.getHome_no()[0];
                String WorkNumber = "";
                String emailID = parseRow.getEmail();
                String company = "Deerwalk Inc.";
                String jobTitle = "";

                ArrayList <ContentProviderOperation> ops = new ArrayList< ContentProviderOperation >();

                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                //------------------------------------------------------ Names
                if (DisplayName != null) {
                    ops.add(ContentProviderOperation.newInsert(
                            ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                            .withValue(
                                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                    DisplayName).build());
                }

                //------------------------------------------------------ Mobile Number
                if (MobileNumber != null) {
                    ops.add(ContentProviderOperation.
                            newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                            .build());
                }

                //------------------------------------------------------ Home Numbers
                if (HomeNumber != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, HomeNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                            .build());
                }

                //------------------------------------------------------ Work Numbers
                if (WorkNumber != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, WorkNumber)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                    ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                            .build());
                }

                //------------------------------------------------------ Email
                if (emailID != null) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, emailID)
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .build());
                }

                //------------------------------------------------------ Organization
                if (!company.equals("") && !jobTitle.equals("")) {
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                            .withValue(ContactsContract.Data.MIMETYPE,
                                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                            .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, jobTitle)
                            .withValue(ContactsContract.CommonDataKinds.Organization.TYPE, ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
                            .build());
                }

                // Asking the Contact provider to create a new contact
                try {
                    context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    Toast.makeText(context, "Added to Contacts", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * @param parseRow
     * @return arraylist of all information in a HashMap way i.e. key and value.. e.g. (PHONE_NO,9841814809)
     */
    private ArrayList<HashMapArray> initializeMenu(ParseRow parseRow) {
        ArrayList<HashMapArray> hashMapArrays = new ArrayList<>();
        for (String no : parseRow.getPhone_no()) {
            HashMapArray PHONE_NO = new HashMapArray(possibleMenuItem.PHONE_NO, no.trim());
            if (no != "")
                hashMapArrays.add(PHONE_NO);

        }
        for (String no : parseRow.getHome_no()) {
            HashMapArray HOME_NO = new HashMapArray(possibleMenuItem.HOME_NO, no.trim());
            if (no != "")
                hashMapArrays.add(HOME_NO);

        }

        //some emergency no have multiple no whereas some have detail of single no
        for (int i = 0; i < parseRow.getEmergency_no().length; i++) {
            if (parseRow.getEmergency_no().length > 1 && !isNumeric(parseRow.getEmergency_no()[1])) {
                String displayName = "";
                for (int j = 1; j < parseRow.getEmergency_no().length; j++) {
                    if (j == 1)
                        displayName = parseRow.getEmergency_no()[j].trim();
                    else
                        displayName += " / " + parseRow.getEmergency_no()[j].trim();
                }
                HashMapArray EMERGENCY_NO = new HashMapArray(possibleMenuItem.EMERGENCY_NO, parseRow.getEmergency_no()[i].trim());
                possibleMenuItem.EMERGENCY_NO.setDisplayText(displayName);
                hashMapArrays.add(EMERGENCY_NO);
                break;
            } else {
                HashMapArray EMERGENCY_NO = new HashMapArray(possibleMenuItem.EMERGENCY_NO, parseRow.getEmergency_no()[i].trim());
                possibleMenuItem.EMERGENCY_NO.setDisplayText("Emergency No");
                if (parseRow.getEmergency_no()[i] != "")
                    hashMapArrays.add(EMERGENCY_NO);
            }
        }
        HashMapArray EMAIL = new HashMapArray(possibleMenuItem.EMAIL, parseRow.getEmail());
        if (parseRow.getEmail() != "")
            hashMapArrays.add(EMAIL);
        HashMapArray Personal_EMAIL = new HashMapArray(possibleMenuItem.PERSONAL_EMAIL, parseRow.getPersonal_email());
        if (parseRow.getPersonal_email() != "")
            hashMapArrays.add(Personal_EMAIL);
        return hashMapArrays;
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    @Override
    public BottomSheetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.single_bottom_sheet, parent, false);
        BottomSheetHolder holder = new BottomSheetHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BottomSheetHolder holder, final int position) {
        holder.menuName.setText(bottomSheetList.get(position).getValue());
        //set image only for the first item in each group ie if there are two phone_nos only one gets an icon
        if (position == 0 || !bottomSheetList.get(position).getGroup().equals(bottomSheetList.get(position - 1).getGroup())) {
            holder.menuIco.setImageResource(getIcon(bottomSheetList.get(position).getKey()));
            //set a red color for emergency no
            if (bottomSheetList.get(position).getKey().equals(possibleMenuItem.EMERGENCY_NO)) {
                int color = Color.RED; //The color u want
                holder.menuIco.setColorFilter(color);
            } else {
                int color = context.getResources().getColor(R.color.primaryColor);
                holder.menuIco.setColorFilter(color);
            }
        } else {//set null image
            holder.menuIco.setImageResource(0);
        }
        holder.menuType.setText(bottomSheetList.get(position).getDisplayText().toString());
        holder.bottomSheetMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIntent(bottomSheetList.get(position));
            }
        });
    }

    private void createIntent(HashMapArray hashMapArray) {
        switch (hashMapArray.getKey()) {
            case EMAIL:
            case PERSONAL_EMAIL:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                String to = hashMapArray.getValue();
                i.putExtra(Intent.EXTRA_EMAIL, to);
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "Send Email Through");
                context.startActivity(chooser);
                break;
            case EMERGENCY_NO:
            case HOME_NO:
            case PHONE_NO:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + hashMapArray.getValue().trim()));
                Intent chooser1 = Intent.createChooser(callIntent, "Call Using");
                context.startActivity(chooser1);
                break;
        }
    }

    //returns icons for each possible MenuItem
    int getIcon(possibleMenuItem key) {
        int resource;
        switch (key) {
            case EMAIL:
                resource = R.drawable.ic_mail_white_24dp;
                break;
            case EMERGENCY_NO:
                resource = R.drawable.ic_call_white_24dp;
                break;
            case HOME_NO:
                resource = R.drawable.ic_home_white_24dp;
                break;
            case PHONE_NO:
                resource = R.drawable.ic_call_white_24dp;
                break;
            default:
                resource = R.drawable.abc_ic_clear_mtrl_alpha;
        }
        return resource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return bottomSheetList.size();
    }

    class BottomSheetHolder extends RecyclerView.ViewHolder {
        TextView menuName, menuType;
        ImageView menuIco;
        LinearLayout bottomSheetMenu;

        public BottomSheetHolder(View itemView) {
            super(itemView);
            menuIco = (ImageView) itemView.findViewById(R.id.bottomSheetIcon);
            menuName = (TextView) itemView.findViewById(R.id.bottomSheetTitle);
            menuType = (TextView) itemView.findViewById(R.id.bottomSheetType);
            bottomSheetMenu = (LinearLayout) itemView.findViewById(R.id.bottomSheetMenu);
        }
    }


    //A HASHMAP type arrayList with key and value pair
    class HashMapArray {
        possibleMenuItem key;
        String value;

        public HashMapArray(possibleMenuItem key, String value) {
            this.key = key;
            this.value = value;
        }

        public possibleMenuItem getKey() {
            return key;
        }

        public String getGroup() {
            return key.getGroup();
        }

        public String getDisplayText() {
            return key.getDisplayText();
        }

        public String getValue() {
            return value;
        }
    }


    //list of all possible menu items
    enum possibleMenuItem {
        EMAIL("EMAIL", "Work"),
        EMERGENCY_NO("EMGPHONE", "Emergency"),
        HOME_NO("HOME", "Home"),
        PHONE_NO("PHONE", "Personal"),
        PERSONAL_EMAIL("EMAIL", "Personal");

        public void setDisplayText(String displayText) {
            this.displayText = displayText;
        }

        String group;
        String displayText;

        possibleMenuItem(String group, String displayText) {
            this.displayText = displayText;
            this.group = group;
        }

        public String getGroup() {
            return group;
        }

        public String getDisplayText() {
            return displayText;
        }
    }
}

