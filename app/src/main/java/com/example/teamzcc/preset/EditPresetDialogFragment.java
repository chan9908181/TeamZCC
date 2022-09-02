package com.example.teamzcc.preset;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.teamzcc.R;

public class EditPresetDialogFragment extends DialogFragment {
    private Preset newPreset;
    private Preset oldPreset;
    private Boolean edited;

    public Preset getNewPreset() {
        return newPreset;
    }

    public Boolean getEdited() {
        return edited;
    }

    public Preset getOldPreset() {
        return oldPreset;
    }

    //Interface required to pass the event in the dialog back to its host
    // https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public interface EditPresetDialogListener {
        public void onPresetEditorCancelClick(EditPresetDialogFragment dialog);

        public void onPresetEditorSaveClick(EditPresetDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    EditPresetDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (EditPresetDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //data received from the main activity
        Bundle data;
        if (getArguments()!=null) {
            data = getArguments();
            oldPreset = new Preset(data.getString("activity"), data.getString("color"));
            edited = true;
        } else {
            data = new Bundle();
            edited = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates the view for the popup window
        LayoutInflater inflater = this.getLayoutInflater();
        View presetEditorView = inflater.inflate(R.layout.dialogue_edit_preset, null);
        builder.setView(presetEditorView);
        //set text of activity field if editing
        if (!data.isEmpty()) {
            ((EditText) presetEditorView.findViewById(R.id.editTextActivityName)).setText(data.getString("activity"));
        }
        //populates the dropdown menu of colors
        Spinner presetColorMenu = presetEditorView.findViewById(R.id.presetColorMenu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.availableColors, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        //set color of the preset if editing
        if (!data.isEmpty()) {
            presetColorMenu.setSelection(adapter.getPosition(data.getString("color")));
        }
        presetColorMenu.setAdapter(adapter);


        //set values according to the current preset if the editor is called on a existing preset
//        if (!data.isEmpty()) {
//            //TODO
//            ((EditText) getActivity().findViewById(R.id.editTextActivityName)).setText(data.getString("activity"));
//            presetColorMenu.setSelection(adapter.getPosition(data.getString("color")));
//        }

        //Buttons' functionality
        Button cancelButton = (Button) presetEditorView.findViewById(R.id.buttonCancel);
        Button saveButton = (Button) presetEditorView.findViewById(R.id.buttonSave);
        //Save -> save and send back the specified preset
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempName = ((EditText) presetEditorView.findViewById(R.id.editTextActivityName)).getText().toString();
                String tempColor = ((Spinner) presetEditorView.findViewById(R.id.presetColorMenu)).getSelectedItem().toString();
                newPreset = new Preset(tempName, tempColor);
                listener.onPresetEditorSaveClick(EditPresetDialogFragment.this);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPresetEditorCancelClick(EditPresetDialogFragment.this);
            }
        });

        return builder.create();
    }
}
