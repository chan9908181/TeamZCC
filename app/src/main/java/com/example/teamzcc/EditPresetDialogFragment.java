package com.example.teamzcc;

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

public class EditPresetDialogFragment extends DialogFragment {
    public Preset tempPreset;

    //Interface required to pass the event in the dialog back to its host
    // https://developer.android.com/guide/topics/ui/dialogs#PassingEvents
    public interface EditPresetDialogListener {
        public void onPresetCancelClick(EditPresetDialogFragment dialog);
        public void onPresetSaveClick(EditPresetDialogFragment dialog);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //creates the view for the dialog popup window
        LayoutInflater inflater = this.getLayoutInflater();
        final View presetEditorView = inflater.inflate(R.layout.activity_edit_preset, null);
        builder.setView(presetEditorView);

        //populates the dropdown menu for colors
        Spinner presetColorMenu = presetEditorView.findViewById(R.id.presetColorMenu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.availableColors, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        presetColorMenu.setAdapter(adapter);

        //Buttons' functionality
        Button cancelButton = (Button) presetEditorView.findViewById(R.id.buttonCancel);
        Button saveButton = (Button) presetEditorView.findViewById(R.id.buttonSave);
        //Save -> save and send back the specified preset
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String tempName = ((EditText) presetEditorView.findViewById(R.id.editTextActivityName)).getText().toString();
                String tempColor = ((Spinner) presetEditorView.findViewById(R.id.presetColorMenu)).getSelectedItem().toString();
                tempPreset = new Preset(tempName, tempColor);
                listener.onPresetSaveClick(EditPresetDialogFragment.this);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                listener.onPresetCancelClick(EditPresetDialogFragment.this);
            }
        });

        return builder.create();

    }
}
