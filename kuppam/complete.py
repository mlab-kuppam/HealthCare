import MySQLdb

try:
	conn = MySQLdb.connect("127.0.0.1","root","kuppam","healthcaredb")
	print("SUCCESSFULLY CONNECTED TO DB")
except(Exception):
	print("Something Went Wrong!\nCheck ur Connection")
	exit(1)
curr = conn.cursor()

def summ():
	full_dict = {"skin":{"Scabies":"sc",	"Pityriasis":"pi",	"Phrynoderm":"ph",	"Pediculosi":"pe",	"Pityriasis":"pity",	"Impetigo":"im",	"Papularurt":"pap",	"Tinea Crus":"tc",	"Miliaria":"mi",	"Viral Wart":"vi",	"Xerosis":"xerosis",	"Referral":"referal"},
				"ent":{	"Otitis Ext(right)":"oe_r",	"Otitis Ext(left)":"oe_l",	"AOM Right":"as_r",	"AOM Left":"as_l",	"CSOM Right":"cs_r",	"CSOM Left":"cs_l",	"Impacted Wax(right)":"iw_r",	"Impacted W(left)":"iw_l",	"Impaired Hearing(right)":"ih_r",	"Impaired Hearing(left)":"ih_l",	"H/O Epistaxis":"ep",	"Adenotonsilitis":"ad",	"Pharyngitis":"ph",	"Allergic Rhinitis":"ar",	"Speech Deffects":"sd",	"URTI":"urti",	"cleft lip":"cleft","Referral":"referal"},
				"eye":{	"No Color-Vision(right)":"cv_r",	"No Color-Vision(left)":"cv_l",	"Bitot's spot(right)":"bs_r",	"Bitot's spot(left)":"bs_l",	"Allergic Conjunctivitis(right)":"ac_r",	"Allergic Conjunctivitis(left)":"ac_l",	"Night Blindness":"nb","Congenital ptosis(right)":"cp_r",	"Congenital ptosis(left)":"cp_l",	"Congenital devolopmental cataract(right)":"cdc_r",	"Congenital devolopmental cataract(left)":"cdc_l",	"Amblyopia(right)":"am_r",	"Amblyopia(left)":"am_l",	"Nystagmus(right)":"ny_r",	"Nystagmus(left)":"ny_l","Fundus Exam(right)":"fe_r","Fundus Exam(left)":"fe_l","Referral":"referal"}, 
				"health1":{	"Frequency of skipping Breakfast":"frequencyFood","Over Night Food":"over_night_food","Both Parents Working":"both_parents",	"Untrimmed Nails":"ph_n",	"Irregular Bathing":"ph_b",	"Poor Grooming":"ph_g",	"Bad Oral Care":"ph_oc",	"Age of Menarche":"ph_am",	"Sanitary Napkin":"ph_am_sn",	"Clinical Anemia":"ca",	"Dental Care":"oe_dc",	"Fluorosis":"oe_f",	"Gingivitis":"oe_g",	"Oral Ulcer":"oe_ou",	"Trauma of ":"oe_trauma",	"Spacing":"oe_spacing",	"Crowding":"oe_crowding","Oral Referal":"oe_referal",	"Chronic Co":"rs_chronic",	"Brochial Asthma":"rs_bronchial",	"Adventitous sounds":"rs_sounds",	"Abnormal Heart Sounds":"cvs_ahs"},
				"health2":{	"Acute gastro-enteritis":"gs_ag",	"Worm Infestation":"gs_wi",	"Passing Worms":"gs_wi_pw",	"Prutitus Ani":"gs_wi_pa",	"Pain Abdomen":"gs_wi_pab",	"Skin Lesions":"gs_wi_sl",	"Deformities":"ms_d",	"Bow Legs":"ms_d_bl",	"Knocked Kness":"ms_d_kk",	"Injury Related Mal-Union":"ms_d_irm",	"Seizures":"ns_s",	"Treatment Taken":"ns_s_tt",	"ADHD":"bp_adhd",	"UTI":"gus_uti",	"H/O burning Micturition":"gus_uti_bm",	"Increased Frequency":"gus_uti_if",	"dribbling":"gus_uti_dr",	"Bedwetting":"gus_bed",	"Vitamin C Deficiency":"vt_c",	"Bleeding Gums":"vt_c_bg",	"Petechial Haemorrhages":"vt_c_ph",	"Vitamin B Complex Deficiency":"vt_b",	"Angular Chelitis":"vt_b_ac",	"Geographical Tongue":"vt_b_gt",	"Referral":"referal"}}


	query = "Select COUNT(DISTINCT child_id) from %(1)s Where %(2)s = 1"
	query2 = "Select COUNT(DISTINCT child_id) from %(1)s Where %(2)s = 0"
	query3 = "Select COUNT(DISTINCT child_id) from %(1)s Where %(2)s = 2"
	ls_check = ["cv_l","cv_r","ph_oc","ph_n","ph_g","ph_b","fe_r","fe_l"]

	fo = open("SUMMARY/SUMMARY.html", "wb")
        fo.write("<html>\n<body>\n<pre>")
	fo.write("""COMPLETE DATABASE SUMMARY\n\n""")

	for part,dis in full_dict.items():
		fo.write(part.upper()+":=>\n\n")
		for ac_name,col_name in dis.items():
			if col_name in ls_check:
				full_query = query2%{"1":part,"2":col_name}
			elif col_name == "ca":
				full_query = query3%{"1":part,"2":col_name}
			else:
				full_query = query%{"1":part,"2":col_name}

			curr.execute(full_query)
			row = curr.fetchone()
			for r in row:
				if(r>0):
					fo.write(ac_name+" = "+str(r)+"\n")
		fo.write("\n**************************************************\n")

	# Close opend file
        fo.write("</html>\n</body>\n</pre>")
	fo.close()
	return

def ref():
	fo = open("SUMMARY/REFERAL.html", "wb")
        fo.write("<html>\n<body>\n<pre>")
	fo.write("""COMPLETE REFERAL SUMMARY\n\n\n""");
	lol = 0
	ref_ls = {"skin","ent","eye","health2","health1"}

	curr.execute("SELECT name,school_id from school")
	row = curr.fetchall()
	for r in row:
		fo.write(r[0]+"\n")
		fo.write("----------------------------------------------------\n")
		curr.execute("SELECT child_id,name from child where school_id = %(1)s"%{"1":r[1]})
		chd = curr.fetchall()
		for k in chd:
			for i in ref_ls:
				if i == "health1":
					curr.execute("SELECT oe_referal from health1 where child_id = %(1)s"%{"1":k[0]})
				else:
					curr.execute("SELECT referal from %(1)s where child_id = %(2)s"%{"1":i,"2":k[0]})
				coun = curr.fetchone()
				if coun:
					if coun[0]:
						lol+=1
						fo.write("["+k[0]+"] - "+k[1]+" => "+i+"\n")
		fo.write("TOTAL STUDENTS FOR REFERAL = "+str(lol)+"\n")
		lol = 0
		fo.write("***********************************************************\n\n\n")
        fo.write("</html>\n</body>\n</pre>")
	fo.close
	return

summ()
ref()