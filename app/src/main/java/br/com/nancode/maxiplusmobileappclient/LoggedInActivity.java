package br.com.nancode.maxiplusmobileappclient;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import it.sephiroth.android.library.exif2.ExifInterface;
import it.sephiroth.android.library.exif2.ExifTag;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.nancode.maxiplusmobileappclient.Model.logModel;
import br.com.nancode.maxiplusmobileappclient.Repository.logRepository;
import br.com.nancode.maxiplusmobileappclient.Repository.userRepository;
import br.com.nancode.maxiplusmobileappclient.CircleImageView.CircleImageView;


public class LoggedInActivity extends AppCompatActivity {

    private static final String TAG = "MainTabFragment";
    private int PICK_IMAGE_REQUEST = 1;
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mviewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mviewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mviewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        tabLayout.bringToFront();

        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        List<logModel> logs = new ArrayList<logModel>();
        logs = lR.SelecionarTodos();
        if(!logs.isEmpty()){
            logModel log = new logModel();
            JSONObject jObject = new JSONObject();
            Integer i;
            String json= "";
            String id = getIntent().getStringExtra("EXTRA_SESSION_ID_EXTERNO");
            String user = getIntent().getStringExtra("EXTRA_SESSION_LOGIN");
            String pass = getIntent().getStringExtra("EXTRA_SESSION_PASS");
            try {
                for (i = 0; i < logs.size(); i++) {
                    log = logs.get(i);
                    jObject.put("logdata" + i, log.getDate());
                    jObject.put("logevento" + i, log.getEvento());
                }
                json = jObject.toString();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            new LogUpdateActivity(LoggedInActivity.this).execute(json, id, user, pass);
        }
    }

    public void logOut(View view) {
        userRepository uR = new userRepository(LoggedInActivity.this.getApplicationContext());
        uR.LogOut();
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário fez logout");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);
        LoggedInActivity.this.finish();
    }

    public void AbrirChat(View view) {
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário pressinou botão para iniciar chat");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);
    }

    public void MudarFoto(View view) {
        findViewById(R.id.CircleImageViewUserPhoto).setEnabled(false);
        logRepository lR = new logRepository(LoggedInActivity.this.getApplicationContext());
        logModel log = new logModel();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        log.setEvento("Usuário pressinou botão para mudar foto de perfil");
        log.setDate(df.format(c.getTime()));

        lR.Salvar(log);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.loggedinactivity_user_tab_selectpicture)), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        findViewById(R.id.CircleImageViewUserPhoto).setEnabled(true);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Verificou que a imagem foi escolhida com sucesso

            Uri uri = data.getData(); //salva as informações da imagem no uri

            try {
                int width = MediaStore.Images.Media.getBitmap(getContentResolver(), uri).getWidth(); //coleta largura da imagem
                int height = MediaStore.Images.Media.getBitmap(getContentResolver(), uri).getHeight(); //coleta altura da imagem
                float ratioBitmap = (float) width / (float) height; //calcula proporção da imagem
                if(ratioBitmap > 1){ //verifica se largura é maior que altura
                    width = 320; //se for, limita largura
                    height = (int)(320 / ratioBitmap); // e redimensiona altura na proporção correta
                }
                else{ //se não
                    height = 320; //limita a altura
                    width = (int)(320 * ratioBitmap); //e redimensiona a largura na proporção correta
                }
                CircleImageView CIV = (CircleImageView) findViewById(R.id.CircleImageViewUserPhoto);

                ExifInterface exif = new ExifInterface(); //ATENÇÃO: EXIF DE BIBLIOTECA EXTERNA!!!
                exif.readExif(getContentResolver().openInputStream(uri), ExifInterface.Options.OPTION_ALL);
                ExifTag tag=exif.getTag(ExifInterface.TAG_ORIENTATION); //pega orientação da imagem
                int orientation=(tag==null ? -1 : tag.getValueAsInt(-1));

                switch (orientation) { //se imagem estiver rotacionada, corrige ela
                    case 6: //90 graus
                        CIV.setImageBitmap(rotateBitmap(Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), width, height, true),90));
                        break;
                    case 3: //180 graus
                        CIV.setImageBitmap(rotateBitmap(Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), width, height, true),180));
                        break;

                    case 8: //270 graus
                        CIV.setImageBitmap(rotateBitmap(Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), width, height, true),270));
                        break;
                    default: //imagem correta
                        CIV.setImageBitmap(Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), width, height, true));
                        break;
                }
                try {
                    ContextWrapper cw = new ContextWrapper(getApplicationContext());
                    // path to /data/data/yourapp/app_data/imageDir
                    File directory = cw.getDir("IMAGES", Context.MODE_PRIVATE);
                    // Create imageDir
                    File mypath=new File(directory,"FOTO_PERFIL");
                    FileOutputStream fos = new FileOutputStream(mypath);
                    ((BitmapDrawable) CIV.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ((BitmapDrawable) CIV.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    String id = getIntent().getStringExtra("EXTRA_SESSION_ID_EXTERNO");
                    String user = getIntent().getStringExtra("EXTRA_SESSION_LOGIN");
                    String pass = getIntent().getStringExtra("EXTRA_SESSION_PASS");
                    new ProfilePictureUpdateActivity(LoggedInActivity.this).execute(encodedImage, id, user, pass);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }



    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoggedInUserFragment(), getResources().getString(R.string.loggedinactivity_user_tab_title));
        adapter.addFragment(new LoggedInCarFragment(), getResources().getString(R.string.loggedinactivity_car_tab_title));
        adapter.addFragment(new LoggedInServicesFragment(), getResources().getString(R.string.loggedinactivity_services_tab_title));

        viewPager.setAdapter(adapter);
    }
}
