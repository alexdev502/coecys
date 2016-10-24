package edu.fiusac.coecys.activity_ditail.view.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.fiusac.coecys.R;
import edu.fiusac.coecys.model.ActivityDitail;
import edu.fiusac.coecys.utils.ResourceIcon;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class InfoFragment extends Fragment {
    @BindView(R.id.tvTitulo) TextView tvTiitulo;
    @BindView(R.id.tvDescripcion) TextView tvDescripcion;
    @BindView(R.id.tvLugar) TextView tvLugar;
    @BindView(R.id.tvHorario) TextView tvHorario;
    @BindView(R.id.tvSpeaker) TextView tvSpeakear;
    @BindView(R.id.tvCompania) TextView tvCompania;
    @BindView(R.id.tvCoecys) TextView tvCoecys;
    @BindView(R.id.tvCoecysContact) TextView tvContact;
    @BindView(R.id.civIcon) CircleImageView civIcon;

    private String dateTimeStart, dateTimeEnd;

    Unbinder unbinder;

    public InfoFragment() {
    }

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_activity, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @OnClick(R.id.fabCalendar)
    public void clickCalendar(View view) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle(R.string.calendar);
        alertBuilder.setMessage(R.string.calendar_message);
        alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                addToCalendar();
            } });
        alertBuilder.setNegativeButton(android.R.string.cancel, null);
        alertBuilder.show();
    }


    public void setInformation(ActivityDitail activityDitail) {
        if(activityDitail!=null) {
            this.tvTiitulo.setText(activityDitail.getTitle());
            this.tvDescripcion.setText(activityDitail.getDescription());
            String strPlace = activityDitail.getPlace() + "\n" + activityDitail.getAddress();
            this.tvLugar.setText(strPlace);
            String strTime = activityDitail.getStartTime() + " - " + activityDitail.getEndTime();
            this.tvHorario.setText(strTime);
            this.tvSpeakear.setText(activityDitail.getSpeaker());
            this.tvCompania.setText(activityDitail.getCompany());
            this.tvCoecys.setText(activityDitail.getInCharge());
            this.tvContact.setText(activityDitail.getInChargePhone());
            this.civIcon.setImageResource(ResourceIcon.getResource(activityDitail.getIcon()));

            this.dateTimeStart = activityDitail.getDate()+" "+activityDitail.getStartTime();
            this.dateTimeEnd = activityDitail.getDate()+" "+activityDitail.getEndTime();
        }
    }

    private void addToCalendar() {
        if(this.dateTimeStart == null){
            this.messageAlgoASalidoMal();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date dateStart, dateEnd;
        try {
            dateStart = dateFormat.parse(this.dateTimeStart);
            dateEnd = dateFormat.parse(this.dateTimeEnd);
        } catch (ParseException e) {
            e.printStackTrace();
            this.messageAlgoASalidoMal();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateStart.getTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEnd.getTime());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.TITLE, tvTiitulo.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, tvDescripcion.getText().toString());

        startActivity(intent);
    }

    private void messageAlgoASalidoMal(){
        Snackbar.make(getView(), R.string.algo_a_salido_mal, Snackbar.LENGTH_LONG).show();
    }
}
