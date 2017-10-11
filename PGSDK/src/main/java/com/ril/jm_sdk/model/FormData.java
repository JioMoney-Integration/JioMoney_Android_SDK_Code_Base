package com.ril.jm_sdk.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RIL on 28-09-2017.
 * Pacellable implementation for transfering data while navigating activity.
 */
public class FormData implements Parcelable {
    Map<String, String> formData = new HashMap<>();

    public FormData(Map<String, String> formData) {
        this.formData = formData;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        int N = formData.size();
        dest.writeInt(N);
        if (N > 0) {
            for (Map.Entry<String, String> entry : formData.entrySet()) {
                dest.writeString(entry.getKey());
                String dat = entry.getValue();
                dest.writeString(dat);
            }
        }
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FormData> CREATOR = new Creator<FormData>() {
        @Override
        public FormData createFromParcel(Parcel in) {
            return new FormData(in);
        }

        @Override
        public FormData[] newArray(int size) {
            return new FormData[size];
        }
    };

    private FormData(Parcel source) {
        int formDataCount = source.readInt();
        for (int i = 0; i < formDataCount; i++) {
            String key = source.readString();
            String value = source.readString();
            formData.put(key, value);
        }
    }
}
