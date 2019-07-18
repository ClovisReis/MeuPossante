package lucas.com.meupossante;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<MenuModel> listDataHeader;
    private HashMap<MenuModel, List<MenuModel>> listDataChild;

    public ExpandableListAdapter(Context context, List<MenuModel> listDataHeader,
                                 HashMap<MenuModel, List<MenuModel>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public MenuModel getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).menuName;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_child, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);


            if (childText.equals("meupossante")) {
                ImageView icones = (ImageView) convertView.findViewById(R.id.childiconeLista);
                icones.setImageResource(R.drawable.ic_menu_icone_instagram);

            } else if (childText.equals("aplicativomeupossante")) {
                ImageView icones = (ImageView) convertView.findViewById(R.id.childiconeLista);
                icones.setImageResource(R.drawable.ic_menu_icone_facebook);

            } else if (childText.equals("aplicativomeupossante@gmail.com")) {
                ImageView icones = (ImageView) convertView.findViewById(R.id.childiconeLista);
                icones.setImageResource(R.drawable.ic_menu_icone_email);
            }
            else{
                ImageView icones = (ImageView) convertView.findViewById(R.id.childiconeLista);
                icones.setImageResource(R.drawable.ic_arrow_forward_black_12dp);
            }




        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public MenuModel getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).menuName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }


        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);



        if(headerTitle.equals("Veículo")){
            ImageView icones = (ImageView) convertView.findViewById(R.id.iconeLista);
            icones.setImageResource(R.drawable.ic_carro);
        }
        else if(headerTitle.equals("Configurações")){
            ImageView icones = (ImageView) convertView.findViewById(R.id.iconeLista);
            icones.setImageResource(R.drawable.ic_menu_maintenance);
        }
        else if(headerTitle.equals("Comprar Aplicativo")){
            ImageView icones = (ImageView) convertView.findViewById(R.id.iconeLista);
            icones.setImageResource(R.drawable.ic_menu_icone_compra_app);
        }
        else if(headerTitle.equals("Fale com a gente")){
            ImageView icones = (ImageView) convertView.findViewById(R.id.iconeLista);
            icones.setImageResource(R.drawable.ic_menu_icone_fale_conosco);
        }



        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}