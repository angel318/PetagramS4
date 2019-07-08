package com.example.petagram;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ContactoActivity extends AppCompatActivity {

    private TextView tvNombre,tvCorreo,tvMensaje;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        tvNombre = (EditText)findViewById(R.id.etNombre);
        tvCorreo = (EditText)findViewById(R.id.etCorreo);
        tvMensaje = (EditText)findViewById(R.id.etMensaje);
        btnEnviar = (Button)findViewById(R.id.btnEnviar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = tvNombre.getText().toString();
                String correo = tvCorreo.getText().toString();
                String mensaje = tvMensaje.getText().toString();

                final String email = "CAMBIARCORREO",pass="CAMBIARCONTRASEÑA";
                Properties props = new Properties();

                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(email,pass);
                            }
                        });

                String subject = "Petagram mensaje de prueba a "+nombre;

                try {
                    MimeMessage msg = new MimeMessage(session);
                    msg.setFrom(email);
                    msg.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse("cambiar.correo@gmail"));
                    msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
                            correo));
                    msg.setSubject(subject);
                    msg.setSentDate(new Date());
                    msg.setText(mensaje);
                    Transport.send(msg);
                    Snackbar.make(v,"Mensaje enviado",Snackbar.LENGTH_LONG).show();
                } catch (MessagingException mex) {
                    Snackbar.make(v,"Envio fallido, error: " + mex,Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
