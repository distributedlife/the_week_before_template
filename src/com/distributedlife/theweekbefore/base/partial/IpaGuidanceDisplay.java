package com.distributedlife.theweekbefore.base.partial;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.distributedlife.language.ipa.WordGuide;
import com.distributedlife.theweekbefore.base.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpaGuidanceDisplay extends ArrayAdapter<String> {
    private Typeface font = null;
    private Context context;
    private List<String> ipa;
    private Activity owner;
    private WordGuide wordGuide;

    public IpaGuidanceDisplay(Context context, List<String> ipa, Activity owner, WordGuide wordGuide) {
        super(context, R.id.pronunciation_list, ipa);
        this.context = context;
        this.ipa = ipa;
        this.owner = owner;
        this.wordGuide = wordGuide;

        font = Typeface.createFromAsset(context.getAssets(), "gentium_plus_l.ttf");
    }

    public Map<String, Integer> getAudioMap() {
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("i", R.raw.close_front_unrounded_vowel);
        map.put("y", R.raw.close_front_rounded_vowel);
        map.put("ɨ", R.raw.close_central_unrounded_vowel);
        map.put("ʉ", R.raw.close_central_rounded_vowel);
        map.put("ɯ", R.raw.close_back_unrounded_vowel);
        map.put("u", R.raw.close_back_rounded_vowel);

        map.put("ɪ", R.raw.near_close_near_front_unrounded_vowel);
        map.put("ʏ", R.raw.near_close_near_front_rounded_vowel);
        map.put("ɪ̈", R.raw.near_close_central_unrounded_vowel);
        // map.put("ʊ̈", R.raw.missing);
        map.put("ʊ", R.raw.near_close_near_back_rounded_vowel);

        map.put("e", R.raw.close_mid_front_unrounded_vowel);
        map.put("ø", R.raw.close_mid_front_rounded_vowel);
        map.put("ɘ", R.raw.close_mid_central_unrounded_vowel);
        map.put("ɵ", R.raw.close_mid_central_rounded_vowel);
        map.put("ɤ", R.raw.close_mid_back_unrounded_vowel);
        map.put("o", R.raw.close_mid_back_rounded_vowel);

        map.put("e̞", R.raw.mid_front_unrounded_vowel);
        // map.put("ø̞", R.raw.missing);
        map.put("ə", R.raw.mid_central_vowel);
        // map.put("ɤ̞", R.raw.missing);
        map.put("o̞", R.raw.mid_back_rounded_vowel);

        map.put("ɛ", R.raw.open_mid_front_unrounded_vowel);
        map.put("œ", R.raw.open_mid_front_rounded_vowel);
        map.put("ɜ", R.raw.open_mid_central_unrounded_vowel);
        map.put("ɞ", R.raw.open_mid_central_rounded_vowel);
        map.put("ʌ", R.raw.open_mid_back_unrounded_vowel);
        map.put("ɔ", R.raw.open_mid_back_rounded_vowel);

        map.put("æ", R.raw.near_open_front_unrounded_vowel);
        map.put("ɐ", R.raw.near_open_central_unrounded_vowel);

        map.put("a", R.raw.open_front_unrounded_vowel);
        // map.put("ɶ", R.raw.missing);
        map.put("ä", R.raw.open_central_unrounded_vowel);
        map.put("ɑ", R.raw.open_back_unrounded_vowel);
        map.put("ɒ", R.raw.open_back_rounded_vowel);

        //consonants
        map.put("m", R.raw.bilabial_nasal);
        map.put("ɱ", R.raw.labiodental_nasal);
        // map.put("n̪", R.raw.dental_nasal);
        map.put("n", R.raw.alveolar_nasal);
        map.put("ɳ", R.raw.retroflex_nasal);
        map.put("ɲ", R.raw.palatal_nasal);
        map.put("ŋ", R.raw.velar_nasal);
        map.put("ɴ", R.raw.uvular_nasal);

        map.put("p", R.raw.voiceless_bilabial_stop);
        map.put("b", R.raw.voiced_bilabial_stop);
        // map.put("p̪", R.raw.missing);
        // map.put("b̪", R.raw.missing);
        // map.put("t̪", R.raw.missing);
        // map.put("d̪", R.raw.missing);
        map.put("t", R.raw.voiceless_alveolar_stop);
        map.put("d", R.raw.voiced_alveolar_stop);
        map.put("ʈ", R.raw.voiceless_retroflex_sibilant);
        map.put("ɖ", R.raw.voiced_retroflex_sibilant);
        map.put("c", R.raw.voiceless_palatal_plosive);
        map.put("ɟ", R.raw.voiced_palatal_plosive);
        map.put("k", R.raw.voiceless_palatal_plosive);
        map.put("ɡ", R.raw.voiced_palatal_plosive);
        map.put("q", R.raw.voiceless_uvular_plosive);
//        map.put("ɢ", R.raw.voiced_uvular_stop);
        map.put("ʡ", R.raw.epiglottal_stop);
        map.put("ʔ", R.raw.glottal_stop);

        map.put("ɸ", R.raw.voiceless_bilabial_fricative);
        map.put("β", R.raw.voiced_bilabial_fricative);
        map.put("f", R.raw.voiceless_labiodental_fricative);
        map.put("v", R.raw.voiced_labiodental_fricative);
        map.put("θ", R.raw.voiceless_dental_fricative);
        map.put("ð", R.raw.voiced_dental_fricative);
        map.put("s", R.raw.voiceless_alveolar_sibilant);
        map.put("z", R.raw.voiced_alveolar_sibilant);
        map.put("ʃ", R.raw.voiceless_palato_alveolar_sibilant);
        map.put("ʒ", R.raw.voiced_palato_alveolar_sibilant);
        map.put("ʂ", R.raw.voiceless_retroflex_sibilant);
        map.put("ʐ", R.raw.voiced_retroflex_sibilant);
        map.put("ç", R.raw.voiceless_palatal_fricative);
        map.put("ʝ", R.raw.voiced_palatal_fricative);
        map.put("x", R.raw.voiceless_velar_fricative);
        map.put("ɣ", R.raw.voiced_velar_fricative);
        map.put("χ", R.raw.voiceless_uvular_fricative);
        map.put("ʁ", R.raw.voiced_uvular_fricative);
        map.put("ħ", R.raw.voiceless_pharyngeal_fricative);
        map.put("ʕ", R.raw.voiced_pharyngeal_fricative);
        map.put("ʜ", R.raw.voiceless_epiglottal_fricative);
        map.put("ʢ", R.raw.voiced_epiglottal_fricative);
        map.put("h", R.raw.voiceless_glottal_fricative);
        map.put("ɦ", R.raw.voiced_glottal_fricative);

        map.put("ʋ", R.raw.labiodental_approximant);
        map.put("ɹ", R.raw.alveolar_approximant);
        map.put("ɻ", R.raw.retroflex_approximant);
        map.put("j", R.raw.palatal_approximant);
        map.put("ɰ", R.raw.voiced_velar_approximant);

        map.put("ʙ", R.raw.bilabial_trill);
        map.put("r", R.raw.alveolar_trill);
        map.put("ʀ", R.raw.uvular_trill);

        map.put("ⱱ", R.raw.labiodental_flap);
        map.put("ɾ", R.raw.alveolar_flap);
        map.put("ɽ", R.raw.retroflex_flap);

        map.put("ɬ", R.raw.voiceless_alveolar_lateral_fricative);
        map.put("ɮ", R.raw.voiced_alveolar_lateral_fricative);
        map.put("ɭ˔̊", R.raw.voiceless_retroflex_lateral_fricative);
        map.put("ʎ̥˔", R.raw.voiceless_palatal_lateral_fricative);
        map.put("ʟ̝̊", R.raw.voiceless_velar_lateral_fricative);

        map.put("l", R.raw.alveolar_lateral_approximant);
        map.put("ɭ", R.raw.retroflex_lateral_approximant);
        map.put("ʎ", R.raw.palatal_lateral_approximant);
        map.put("ʟ", R.raw.velar_lateral_approximant);

        map.put("ɺ", R.raw.alveolar_lateral_flap);

        return map;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String character = ipa.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ipa_with_example, parent, false);

        Integer sound = getAudioMap().get(character);
        if (sound != null) {
            rowView.setOnClickListener(new PlayIpaSound(owner, sound));
        }

        TextView ipaSymbol = (TextView) rowView.findViewById(R.id.ipaSymbol);
        ipaSymbol.setText(character);
        if (font != null) {
            ipaSymbol.setTypeface(font);
        }

        TextView exampleWord = (TextView) rowView.findViewById(R.id.exampleWord);
        exampleWord.setText(getPhoneticText(character, position));

        return rowView;
    }

    private String getPhoneticText(String part, Integer position) {
        if (part.equals(" ")) {
            return " ";
        } else {
            if (wordGuide.getCharacterCount().equals(0)) {
                return "MISSING";
            } else {
                return String.format("%s", wordGuide.getCharacter(position).getWord());
            }
        }
    }

    private class PlayIpaSound implements View.OnClickListener {
        private MediaPlayer mediaPlayer = null;

        public PlayIpaSound(Activity parent, int sound) {
            try {
                mediaPlayer = MediaPlayer.create(parent, sound);
            } catch (Resources.NotFoundException e) {
                mediaPlayer = null;
            }
        }

        @Override
        public void onClick(View view) {
            if (mediaPlayer == null) {
                return;
            }

            mediaPlayer.start();
        }
    }
}
