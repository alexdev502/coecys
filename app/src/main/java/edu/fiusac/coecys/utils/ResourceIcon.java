package edu.fiusac.coecys.utils;

import edu.fiusac.coecys.R;

/**
 * Created by Mario Alexander Gutierrez Hernandez
 * email: alex.dev502@gmail.com
 */
public class ResourceIcon {

    public static int getResource(int icon){
        switch (icon) {
            case 1: return R.drawable.arduino;
            case 2: return R.drawable.laravel;
            case 3: return R.drawable.datum;
            case 4: return R.drawable.fiusac;
            case 5: return R.drawable.party_dj_adelaide_icon;
            case 6: return R.drawable.eset;
            case 7: return R.drawable.openstack;
            case 8: return R.drawable.bi_icon;
            case 9: return R.drawable.mingob;
            case 10: return R.drawable.upana;
            case 11: return R.drawable.internaciones;
            case 12: return R.drawable.bdg;
            case 13: return R.drawable.microsoft;
            case 14: return R.drawable.mingob_14;
            case 15: return R.drawable.tigo;
            case 16: return R.drawable.url;
            case 17: return R.drawable.cempro;
            case 18: return R.drawable.kio;
            case 19: return R.drawable.concyt;
            case 20: return R.drawable.inacif;
            case 21: return R.drawable.cerveceria;
            case 22: return R.drawable.oracle;
            case 23: return R.drawable.googlecloud;
            case 24: return R.drawable.emprendimiento;
            case 25: return R.drawable.daas;
            case 26: return R.drawable.cloud;
            case 27: return R.drawable.angular;
            case 28: return R.drawable.azure;
            case 29: return R.drawable.gala;
            case 30: return R.drawable.ic_30;
            case 31: return R.drawable.ic_31;
            case 32: return R.drawable.ic_32;
            case 33: return R.drawable.ic_33;
            default:
                return R.drawable.icon_coecys;
        }
    }
}
