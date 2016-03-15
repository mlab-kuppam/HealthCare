import MySQLdb

def create():
	full_dict = {"skin":{"Scabies":"sc",	"Pityriasis":"pi",	"Phrynoderm":"ph",	"Pediculosi":"pe",	"Pityriasis":"pity",	"Impetigo":"im",	"Papularurt":"pap",	"Tinea Crus":"tc",	"Miliaria":"mi",	"Viral Wart":"vi",	"Xerosis":"xerosis",	"Referral":"referal"},
				"ent":{	"Otitis Ext(right)":"oe_r",	"Otitis Ext(left)":"oe_l",	"AOM Right":"as_r",	"AOM Left":"as_l",	"CSOM Right":"cs_r",	"CSOM Left":"cs_l",	"Impacted Wax(right)":"iw_r",	"Impacted W(left)":"iw_l",	"Impaired Hearing(right)":"ih_r",	"Impaired Hearing(left)":"ih_l",	"H/O Epistaxis":"ep",	"Adenotonsilitis":"ad",	"Pharyngitis":"ph",	"Allergic Rhinitis":"ar",	"Speech Deffects":"sd",	"URTI":"urti",	"Referral":"referal"},
				"eye":{	"No Color-Vision(right)":"cv_r",	"No Color-Vision(left)":"cv_l",	"Bitot's spot(right)":"bs_r",	"Bitot's spot(left)":"bs_l",	"Allergic Conjunctivitis(right)":"ac_r",	"Allergic Conjunctivitis(left)":"ac_l",	"Night Blind{ness(right)":"nb_r",	"Night Blindness(left)":"nb_l",	"Congenital ptosis(right)":"cp_r",	"Congenital ptosis(left)":"cp_l",	"Congenital devolopmental cataract(right)":"cdc_r",	"Congenital devolopmental cataract(left)":"cdc_l",	"Amblyopia(right)":"am_r",	"Amblyopia(left)":"am_l",	"Nystagmus(right)":"ny_r",	"Nystagmus(left)":"ny_l",	"Referral":"referal"}, 
				"health1":{	"Both Parents Working":"both_parents",	"Bad Nails":"ph_n",	"Irregular Bathing":"ph_b",	"Bad Grooming":"ph_g",	"Bad Oral Care":"ph_oc",	"Age of Menarche":"ph_am",	"Sanitary Napkin":"ph_am_sn",	"Clinical Anemia":"ca",	"Dental Care":"oe_dc",	"Fluorosis":"oe_f",	"Gingivitis":"oe_g",	"Oral Ulcer":"oe_ou",	"Trauma of ":"oe_trauma",	"Spacing":"oe_spacing",	"Crowding":"oe_crowding",	"cleft lip":"oe_cleft",	"Chronic Co":"rs_chronic",	"Brochial Asthma":"rs_bronchial",	"Adventitous sounds":"rs_sounds",	"Abnormal Heart Sounds":"cvs_ahs"},
				"health2":{	"Acute gastro-enteritis":"gs_ag",	"Worm Infestation":"gs_wi",	"Passing Worms":"gs_wi_pw",	"Prutitus Ani":"gs_wi_pa",	"Pain Abdomen":"gs_wi_pab",	"Skin Lesions":"gs_wi_sl",	"Deformities":"ms_d",	"Bow Legs":"ms_d_bl",	"Knocked Kness":"ms_d_kk",	"Injury Related Mal-Union":"ms_d_irm",	"Seizures":"ns_s",	"Treatment Taken":"ns_s_tt",	"ADHD":"bp_adhd",	"UTI":"gus_uti",	"H/O burning Micturition":"gus_uti_bm",	"Increased Frequency":"gus_uti_if",	"dribbling":"gus_uti_dr",	"Bedwetting":"gus_bed",	"Deformities":"gus_def",	"Congenital Hernias":"gus_def_ch",	"Undescended Testes":"gus_def_ut",	"hypospadiasis":"gus_def_hy",	"phimosis":"gus_def_ph",	"Vitamin C Deficiency":"vt_c",	"Bleeding Gums":"vt_c_bg",	"Petechial Haemorrhages":"vt_c_ph",	"Vitamin B Complex Deficiency":"vt_b",	"Angular Chelitis":"vt_b_ac",	"Geographical Tongue":"vt_b_gt",	"Referral":"referal"}}
	
	'''full_dict_old = {"skin":{"Scabies":"sc","Pityriasis":"pi","Phrynoderm":"ph","Pediculosi":"pe","Pityriasis":"pity","Impetigo":"im","Papularurt":"pap","Tinea Crus":"tc","Miliaria":"mi","Viral Wart":"vi"},
				"ent":{"Otitis Ext(right)":"oe_r","Otitis Ext(left)":"oe_l","ASOM Right":"as_r","ASOM Left":"as_l","CSOM Right":"cs_r","CSOM Left":"cs_l","Impacted Wax(right)":"iw_r","Impacted W(left)":"iw_l","Impaired Hearing(right)":"ih_r","Impaired Hearing(left)":"ih_l","H/O Epistaxis":"ep","Adenotonsilitis":"ad","Pharyngitis":"ph","Allergic Rhinitis":"ar","Speech Deffects":"sd","URTI":"urti"},
				"eye":{"No Color-Vision(right)":"cv_r","No Color-Vision(left)":"cv_l","Bitot's spot(right)":"bs_r","Bitot's spot(left)":"bs_l","Allergic Conjunctivitis(right)":"ac_r","Allergic Conjunctivitis(left)":"ac_l","Night Blindness(right)":"nb_r","Night Blindness(left)":"nb_l","Congenital ptosis(right)":"cp_r","Congenital ptosis(left)":"cp_l","Congenital devolopmental cataract(right)":"cdc_r","Congenital devolopmental cataract(left)":"cdc_l","Amblyopia(right)":"am_r","Amblyopia(left)":"am_l","Nystagmus(right)":"ny_r","Nystagmus(left)":"ny_l"},
				"health1":{"Nails":"ph_n","Bathing":"ph_b","Grooming":"ph_g","Oral Care":"ph_oc","Age of Menarche":"ph_am","Sanitary Napkin":"ph_am_sn","Clinical Anameia":"ca","Dental Care":"oe_dc","Fluorosis":"oe_f","Gingivitis":"oe_g","Oral Ulcer":"oe_ou","Trauma of ":"oe_trauma","Spacing":"oe_spacing","corwding":"oe_crowding","cleft lip":"oe_cleft","Chronic Co":"rs_chronic","Brochial Asthma":"rs_bronchial","Adventitou":"rs_sounds","Abnormal Heart Sounds":"cvs_ahs"},
				"health2":{"Acute gastro-enteritis":"gs_ag","Worm Infestation":"gs_wi","Passing Worms":"gs_wi_pw","Prutitus Ani":"gs_wi_pa","Pain Abdomen":"gs_wi_pab","Skin Lesions":"gs_wi_sl","Deformities":"ms_d","Seizures":"ns_s","On antiepileptic drugs":"ns_s_tt","ADHD":"bp_adhd","UTI":"gus_uti","H/O burning Micturition":"gus_uti_bm","Increased Frequency":"gus_uti_if","dribbling":"gus_uti_dr","Bedwetting":"gus_bed","Deformities":"gus_def","Congenital Hernias":"gus_def_ch","Undescended Testes":"gus_def_ut","hypospadiasis":"gus_def_hy","phimosis":"gus_def_ph","Vitamin C Deficiency":"vt_c","Bleeding Gums":"vt_c_bg","Petechial Haemorrhages":"vt_c_ph","Vitamin B Complex Deficiency":"vt_b","Angular Chelitis":"vt_b_ac","Geographical Tongue":"vt_b_gt","Other Infectious Diseases":"oid","H/O injuries":"hoin"}}'''

	query = "Select COUNT(*) from %(1)s Where %(2)s = 1"
	query2 = "Select COUNT(*) from %(1)s Where %(2)s = 0"
	ls_check = ["cv_l","cv_r","ph_oc","ph_n","ph_g","ph_b"]

	try:
		conn = MySQLdb.connect("127.0.0.1","root","","healthcaredb")
		print("SUCCESSFULLY CONNECTED TO DB")
	except(Exception):
		print("Something Went Wrong!\nCheck ur Connection")
		exit(1)
	curr = conn.cursor()

	fo = open("SUMMARY.doc", "wb")
	fo.write("""COMPLETE DATABASE SUMMARY\n\n""");

	for part,dis in full_dict.items():
		fo.write(part.upper()+":=>\n\n")
		for ac_name,col_name in dis.items():
			if col_name in ls_check:
				full_query = query2%{"1":part,"2":col_name}
			else:
				full_query = query%{"1":part,"2":col_name}

			curr.execute(full_query)
			row = curr.fetchone()
			for r in row:
				if(r>0):
					fo.write(ac_name+" = "+str(r)+"\n")
		fo.write("\n**************************************************\n")

	# Close opend file
	fo.close()
	return
