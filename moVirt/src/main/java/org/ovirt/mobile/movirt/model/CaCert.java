package org.ovirt.mobile.movirt.model;

import android.content.ContentValues;
import android.net.Uri;
import android.renderscript.Element;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.ovirt.mobile.movirt.provider.OVirtContract;
import org.ovirt.mobile.movirt.util.CursorHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.security.cert.Certificate;
import java.sql.Timestamp;

import static org.ovirt.mobile.movirt.provider.OVirtContract.CaCert.TABLE;

@DatabaseTable(tableName = TABLE)
public class CaCert extends BaseEntity<Integer> implements OVirtContract.CaCert {

    @Override
    public Uri getBaseUri() {
        return CONTENT_URI;
    }

    @DatabaseField(columnName = ID, id = true)
    private int id;

    @DatabaseField(columnName = CONTENT, canBeNull = false, dataType = DataType.BYTE_ARRAY)
    private byte[] content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(CONTENT, content);

        return values;
    }

    @Override
    protected void initFromCursorHelper(CursorHelper cursorHelper) {
        setId(cursorHelper.getInt(ID));
        setContent(cursorHelper.getByteArray(CONTENT));
    }

    public Certificate asCertificate() {
        ByteArrayInputStream bis = new ByteArrayInputStream(getContent());
        ObjectInput in = null;
        try {
            try {
                in = new ObjectInputStream(bis);
                Object o = in.readObject();
                if (o instanceof Certificate) {
                    return (Certificate) o;
                } else {
                    throw new IllegalStateException("The result object is not a Certificate");
                }
            } catch (IOException e) {
                throw new IllegalStateException("Error creating caCert from the blob provided: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Error creating caCert from the blob provided: " + e.getMessage());
            }

        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }
}
