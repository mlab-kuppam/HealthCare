-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 27, 2016 at 05:11 PM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `healthcaredb`
--

-- --------------------------------------------------------

--
-- Table structure for table `child`
--

CREATE TABLE `child` (
  `child_id` varchar(11) NOT NULL,
  `school_id` varchar(8) NOT NULL,
  `name` varchar(140) NOT NULL,
  `dob` varchar(15) DEFAULT NULL,
  `gender` int(1) NOT NULL,
  `standard` int(2) DEFAULT NULL,
  `father_name` varchar(140) DEFAULT NULL,
  `mother_name` varchar(140) DEFAULT NULL,
  `guardian_name` varchar(140) DEFAULT NULL,
  `attendance` float DEFAULT NULL,
  `academic` varchar(2) DEFAULT NULL,
  `aadhar` int(12) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `child`
--

-- --------------------------------------------------------

--
-- Table structure for table `ent`
--

CREATE TABLE `ent` (
  `child_id` varchar(11) NOT NULL,
  `oe_r` tinyint(1) NOT NULL,
  `oe_r_com` varchar(140) DEFAULT NULL,
  `oe_l` tinyint(1) NOT NULL,
  `oe_l_com` varchar(140) DEFAULT NULL,
  `as_r` tinyint(1) NOT NULL,
  `as_r_com` varchar(140) DEFAULT NULL,
  `as_l` tinyint(1) NOT NULL,
  `as_l_com` varchar(140) DEFAULT NULL,
  `cs_r` tinyint(1) NOT NULL,
  `cs_r_com` varchar(140) DEFAULT NULL,
  `cs_l` tinyint(1) NOT NULL,
  `cs_l_com` varchar(140) DEFAULT NULL,
  `iw_r` tinyint(1) NOT NULL,
  `iw_r_com` int(1) DEFAULT NULL,
  `iw_l` tinyint(1) NOT NULL,
  `iw_l_com` int(1) DEFAULT NULL,
  `ih_r` tinyint(1) NOT NULL,
  `ih_r_com` varchar(140) DEFAULT NULL,
  `ih_l` tinyint(1) NOT NULL,
  `ih_l_com` varchar(140) DEFAULT NULL,
  `ep` tinyint(1) NOT NULL,
  `ep_com` varchar(140) DEFAULT NULL,
  `ad` tinyint(1) NOT NULL,
  `ad_com` int(1) DEFAULT NULL,
  `ph` tinyint(1) NOT NULL,
  `ph_com` varchar(140) DEFAULT NULL,
  `ar` tinyint(1) NOT NULL,
  `ar_com` int(1) DEFAULT NULL,
  `sd` tinyint(1) NOT NULL,
  `sd_com` varchar(140) DEFAULT NULL,
  `urti` tinyint(1) NOT NULL,
  `urti_com` varchar(140) DEFAULT NULL,
  `cleft` tinyint(1) NOT NULL,
  `cleft_operated` int(1) NOT NULL,
  `others` varchar(140) DEFAULT NULL,
  `impression` varchar(140) DEFAULT NULL,
  `treatment` varchar(1000) DEFAULT NULL,
  `referal` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ent`
--


-- --------------------------------------------------------

--
-- Table structure for table `eye`
--

CREATE TABLE `eye` (
  `child_id` varchar(11) NOT NULL,
  `vt_r_d` varchar(5) NOT NULL,
  `vt_r_n` varchar(5) NOT NULL,
  `vt_l_d` varchar(5) NOT NULL,
  `vt_l_n` varchar(5) NOT NULL,
  `vt_com` varchar(140) DEFAULT NULL,
  `rc_r_s_d` varchar(5) NOT NULL,
  `rc_r_s_n` varchar(5) NOT NULL,
  `rc_r_c_d` varchar(5) NOT NULL,
  `rc_r_c_n` varchar(5) NOT NULL,
  `rc_r_a_d` varchar(5) NOT NULL,
  `rc_r_a_n` varchar(5) NOT NULL,
  `rc_l_s_d` varchar(5) NOT NULL,
  `rc_l_s_n` varchar(5) NOT NULL,
  `rc_l_c_d` varchar(5) NOT NULL,
  `rc_l_c_n` varchar(5) NOT NULL,
  `rc_l_a_d` varchar(5) NOT NULL,
  `rc_l_a_n` varchar(5) NOT NULL,
  `rc_com` varchar(140) DEFAULT NULL,
  `cv_r` tinyint(1) NOT NULL,
  `cv_r_com` varchar(140) DEFAULT NULL,
  `cv_l` tinyint(1) NOT NULL,
  `cv_l_com` varchar(140) DEFAULT NULL,
  `bs_r` tinyint(1) NOT NULL,
  `bs_r_com` varchar(140) DEFAULT NULL,
  `bs_l` tinyint(1) NOT NULL,
  `bs_l_com` varchar(140) DEFAULT NULL,
  `ac_r` tinyint(1) NOT NULL,
  `ac_r_com` varchar(140) DEFAULT NULL,
  `ac_l` tinyint(1) NOT NULL,
  `ac_l_com` varchar(140) DEFAULT NULL,
  `nb` tinyint(1) NOT NULL,
  `nb_com` varchar(140) DEFAULT NULL,
  `cp_r` tinyint(1) NOT NULL,
  `cp_r_com` varchar(140) DEFAULT NULL,
  `cp_l` tinyint(1) NOT NULL,
  `cp_l_com` varchar(140) DEFAULT NULL,
  `cdc_r` tinyint(1) NOT NULL,
  `cdc_r_com` varchar(140) DEFAULT NULL,
  `cdc_l` tinyint(1) NOT NULL,
  `cdc_l_com` varchar(140) DEFAULT NULL,
  `am_r` tinyint(1) NOT NULL,
  `am_r_com` varchar(140) DEFAULT NULL,
  `am_l` tinyint(1) NOT NULL,
  `am_l_com` varchar(140) DEFAULT NULL,
  `ny_r` tinyint(1) NOT NULL,
  `ny_r_com` varchar(140) DEFAULT NULL,
  `ny_l` tinyint(1) NOT NULL,
  `ny_l_com` varchar(140) DEFAULT NULL,
  `fe_r` tinyint(1) NOT NULL,
  `fe_l` tinyint(1) NOT NULL,
  `others` varchar(140) DEFAULT NULL,
  `impression` varchar(140) DEFAULT NULL,
  `treatment` varchar(1000) DEFAULT NULL,
  `referal` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `eye`
--

-- --------------------------------------------------------

--
-- Table structure for table `health1`
--

CREATE TABLE `health1` (
  `child_id` varchar(11) NOT NULL,
  `frequency` int(1) DEFAULT NULL,
  `frequencyFood` int(1) DEFAULT NULL,
  `over_night_food` int(1) DEFAULT NULL,
  `both_parents` tinyint(1) DEFAULT NULL,
  `height` float(5) NOT NULL,
  `weight` float(5) NOT NULL,
  `wc` float(5) NOT NULL,
  `hc` float(5) NOT NULL,
  `pr` int(3) NOT NULL,
  `bp` varchar(7) NOT NULL,
  `ph_n` tinyint(1) NOT NULL,
  `ph_n_com` varchar(140) DEFAULT NULL,
  `ph_b` tinyint(1) NOT NULL,
  `ph_b_com` varchar(140) DEFAULT NULL,
  `ph_g` tinyint(1) NOT NULL,
  `ph_g_com` varchar(140) DEFAULT NULL,
  `ph_oc` tinyint(1) NOT NULL,
  `ph_oc_com` varchar(140) DEFAULT NULL,
  `ph_am` tinyint(1) NOT NULL,
  `ph_am_sn` tinyint(1) NOT NULL,
  `ph_am_sn_com` varchar(140) DEFAULT NULL,
  `health_others` varchar(140) DEFAULT NULL,
  `ca` int(1) NOT NULL,
  `ca_com` varchar(140) DEFAULT NULL,
  `oe_dc` tinyint(1) NOT NULL,
  `oe_dc_com` varchar(140) DEFAULT NULL,
  `oe_f` tinyint(1) NOT NULL,
  `oe_f_com` varchar(1) DEFAULT NULL,
  `oe_g` tinyint(1) NOT NULL,
  `oe_g_com` varchar(140) DEFAULT NULL,
  `oe_ou` tinyint(1) NOT NULL,
  `oe_ou_com` varchar(140) DEFAULT NULL,
  `oe_trauma` tinyint(1) NOT NULL,
  `oe_trauma_com` varchar(140) DEFAULT NULL,
  `oe_spacing` tinyint(1) NOT NULL,
  `oe_spacing_com` varchar(140) DEFAULT NULL,
  `oe_crowding` tinyint(1) NOT NULL,
  `oe_crowding_com` varchar(140) DEFAULT NULL,
  `oe_referal` tinyint(1) DEFAULT NULL,
  `oe_others` varchar(140) DEFAULT NULL,
  `rs_chronic` tinyint(1) NOT NULL,
  `rs_chronic_com` varchar(140) DEFAULT NULL,
  `rs_bronchial` tinyint(1) NOT NULL,
  `oe_bronchial_medication` int(1) NOT NULL,
  `rs_sounds` tinyint(1) NOT NULL,
  `rs_sounds_com` varchar(140) DEFAULT NULL,
  `rs_others` varchar(140) DEFAULT NULL,
  `cvs_ahs` tinyint(1) NOT NULL,
  `cvs_ahs_com` varchar(140) DEFAULT NULL,
  `cvs_others` varchar(140) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `health1`
--

-- --------------------------------------------------------

--
-- Table structure for table `health2`
--

CREATE TABLE `health2` (
  `child_id` varchar(11) NOT NULL,
  `gs_ag` tinyint(1) NOT NULL,
  `gs_ag_com` varchar(140) DEFAULT NULL,
  `gs_wi` tinyint(1) NOT NULL,
  `gs_wi_pw` tinyint(1) NOT NULL DEFAULT '0',
  `gs_wi_pa` tinyint(1) NOT NULL DEFAULT '0',
  `gs_wi_pab` tinyint(1) NOT NULL DEFAULT '0',
  `gs_wi_sl` tinyint(1) NOT NULL DEFAULT '0',
  `gs_wi_com` varchar(140) DEFAULT NULL,
  `gs_others` varchar(140) DEFAULT NULL,
  `ms_d` tinyint(1) NOT NULL,
  `ms_d_bl` tinyint(1) NOT NULL DEFAULT '0',
  `ms_d_kk` tinyint(1) NOT NULL DEFAULT '0',
  `ms_d_irm` tinyint(1) NOT NULL DEFAULT '0',
  `ms_d_com` varchar(140) DEFAULT NULL,
  `ms_others` varchar(140) DEFAULT NULL,
  `ns_s` tinyint(1) NOT NULL,
  `ns_s_tt` tinyint(1) NOT NULL DEFAULT '0',
  `ns_others` varchar(140) DEFAULT NULL,
  `bp_adhd` tinyint(1) NOT NULL,
  `bp_adhd_com` varchar(140) DEFAULT NULL,
  `bp_others` varchar(140) DEFAULT NULL,
  `gus_uti` tinyint(1) NOT NULL,
  `gus_uti_bm` tinyint(1) NOT NULL DEFAULT '0',
  `gus_uti_if` tinyint(1) NOT NULL DEFAULT '0',
  `gus_uti_dr` tinyint(1) NOT NULL DEFAULT '0',
  `gus_uti_com` varchar(140) DEFAULT NULL,
  `gus_bed` tinyint(1) NOT NULL,
  `gus_bed_com` varchar(140) DEFAULT NULL,
  `gus_def` tinyint(1) NOT NULL,
  `gus_def_ch` tinyint(1) NOT NULL DEFAULT '0',
  `gus_def_ut` tinyint(1) NOT NULL DEFAULT '0',
  `gus_def_hy` tinyint(1) NOT NULL DEFAULT '0',
  `gus_def_ph` tinyint(1) NOT NULL DEFAULT '0',
  `gus_def_com` varchar(140) DEFAULT NULL,
  `gus_others` varchar(140) DEFAULT NULL,
  `vt_c` tinyint(1) NOT NULL,
  `vt_c_bg` tinyint(1) NOT NULL DEFAULT '0',
  `vt_c_ph` tinyint(1) NOT NULL DEFAULT '0',
  `vt_c_com` varchar(140) DEFAULT NULL,
  `vt_b` tinyint(1) NOT NULL,
  `vt_b_ac` tinyint(1) NOT NULL DEFAULT '0',
  `vt_b_gt` tinyint(1) NOT NULL DEFAULT '0',
  `vt_b_com` varchar(140) DEFAULT NULL,
  `vt_others` varchar(140) DEFAULT NULL,
  `others` varchar(140) DEFAULT NULL,
  `impression` varchar(140) DEFAULT NULL,
  `treatment` varchar(1000) DEFAULT NULL,
  `referal` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `health2`
--

-- --------------------------------------------------------

--
-- Table structure for table `images`
--

CREATE TABLE `images` (
  `child_id` varchar(11) NOT NULL,
  `photo_id` varchar(30) NOT NULL,
  `image` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `images`
--

-- --------------------------------------------------------

--
-- Table structure for table `school`
--

CREATE TABLE `school` (
  `school_id` varchar(8) NOT NULL,
  `name` varchar(140) NOT NULL,
  `category` int(1) NOT NULL,
  `type` int(1) NOT NULL,
  `hm_name` varchar(140) NOT NULL,
  `landline` float DEFAULT NULL,
  `mobile` varchar(11) NOT NULL,
  `email` varchar(254) DEFAULT NULL,
  `l_busy_places` tinyint(1) DEFAULT NULL,
  `l_fencing` tinyint(1) DEFAULT NULL,
  `l_hygenic` tinyint(1) DEFAULT NULL,
  `l_playground` tinyint(1) DEFAULT NULL,
  `l_stories` int(2) DEFAULT NULL,
  `r_number` int(2) DEFAULT NULL,
  `r_students` int(3) DEFAULT NULL,
  `hm_office` tinyint(1) DEFAULT NULL,
  `r_lab` tinyint(1) DEFAULT NULL,
  `r_art` tinyint(1) DEFAULT NULL,
  `r_kitchen` tinyint(1) DEFAULT NULL,
  `furniture` tinyint(1) DEFAULT NULL,
  `cross_vent` tinyint(1) DEFAULT NULL,
  `lighting` tinyint(1) DEFAULT NULL,
  `water` int(1) DEFAULT NULL,
  `electricity` tinyint(1) DEFAULT NULL,
  `t_urinal` int(3) DEFAULT NULL,
  `t_latrines` int(3) DEFAULT NULL,
  `t_separate` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `school`
--

-- --------------------------------------------------------

--
-- Table structure for table `skin`
--

CREATE TABLE `skin` (
  `child_id` varchar(11) NOT NULL,
  `sc` tinyint(1) NOT NULL,
  `sc_com` varchar(140) DEFAULT NULL,
  `pi` tinyint(1) NOT NULL,
  `pi_com` varchar(140) DEFAULT NULL,
  `ph` tinyint(1) NOT NULL,
  `ph_com` varchar(140) DEFAULT NULL,
  `pe` tinyint(1) NOT NULL,
  `pe_com` varchar(140) DEFAULT NULL,
  `pity` tinyint(1) NOT NULL,
  `pity_com` varchar(140) DEFAULT NULL,
  `im` tinyint(1) NOT NULL,
  `im_com` varchar(140) DEFAULT NULL,
  `pap` tinyint(1) NOT NULL,
  `pap_com` varchar(140) DEFAULT NULL,
  `tc` tinyint(1) NOT NULL,
  `tc_com` varchar(140) DEFAULT NULL,
  `mi` tinyint(1) NOT NULL,
  `mi_com` varchar(140) DEFAULT NULL,
  `vi` tinyint(1) NOT NULL,
  `vi_com` varchar(140) DEFAULT NULL,
  `xerosis` tinyint(1) NOT NULL,
  `xerosis_com` varchar(140) DEFAULT NULL,
  `others` varchar(140) DEFAULT NULL,
  `impression` varchar(140) DEFAULT NULL,
  `treatment` varchar(1000) DEFAULT NULL,
  `referal` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skin`
--

-- --------------------------------------------------------

--
-- Table structure for table `socio_demographic`
--

CREATE TABLE `socio_demographic` (
  `child_id` varchar(11) NOT NULL,
  `address` varchar(140) NOT NULL,
  `landline` float NOT NULL,
  `mobile` float NOT NULL,
  `type` int(1) NOT NULL,
  `number_members` int(2) NOT NULL,
  `hf_title` varchar(20) NOT NULL,
  `hf_aadhar` int(12) DEFAULT NULL,
  `hf_education` varchar(140) NOT NULL,
  `hf_occupation` varchar(140) NOT NULL,
  `f_education` varchar(140) NOT NULL,
  `f_occupation` varchar(140) NOT NULL,
  `m_education` varchar(140) NOT NULL,
  `m_occupation` varchar(140) NOT NULL,
  `total_income` int(8) NOT NULL,
  `socio_economic` int(1) NOT NULL,
  `hs_type` int(1) DEFAULT NULL,
  `hs_flooring` int(1) DEFAULT NULL,
  `r_overcrowding` tinyint(1) DEFAULT NULL,
  `cross_ventilation` tinyint(1) DEFAULT NULL,
  `lighting` tinyint(1) DEFAULT NULL,
  `kitchen` tinyint(1) DEFAULT NULL,
  `water` int(1) DEFAULT NULL,
  `hygenic_surroundings` tinyint(1) DEFAULT NULL,
  `sanitary_latrine` tinyint(1) DEFAULT NULL,
  `garbage_disposal` int(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `child`
--
ALTER TABLE `child`
  ADD PRIMARY KEY (`child_id`,`timestamp`),
  ADD KEY `school_id` (`school_id`);

--
-- Indexes for table `ent`
--
ALTER TABLE `ent`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Indexes for table `eye`
--
ALTER TABLE `eye`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Indexes for table `health1`
--
ALTER TABLE `health1`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Indexes for table `health2`
--
ALTER TABLE `health2`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Indexes for table `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`child_id`,`photo_id`,`timestamp`);

--
-- Indexes for table `school`
--
ALTER TABLE `school`
  ADD PRIMARY KEY (`school_id`,`timestamp`);

--
-- Indexes for table `skin`
--
ALTER TABLE `skin`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Indexes for table `socio_demographic`
--
ALTER TABLE `socio_demographic`
  ADD PRIMARY KEY (`child_id`,`timestamp`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `child`
--
ALTER TABLE `child`
  ADD CONSTRAINT `child_ibfk_1` FOREIGN KEY (`school_id`) REFERENCES `school` (`school_id`);

--
-- Constraints for table `ent`
--
ALTER TABLE `ent`
  ADD CONSTRAINT `ent_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `eye`
--
ALTER TABLE `eye`
  ADD CONSTRAINT `eye_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `health1`
--
ALTER TABLE `health1`
  ADD CONSTRAINT `health1_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `health2`
--
ALTER TABLE `health2`
  ADD CONSTRAINT `health2_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `images`
--
ALTER TABLE `images`
  ADD CONSTRAINT `images_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `skin`
--
ALTER TABLE `skin`
  ADD CONSTRAINT `skin_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

--
-- Constraints for table `socio_demographic`
--
ALTER TABLE `socio_demographic`
  ADD CONSTRAINT `socio_demographic_ibfk_1` FOREIGN KEY (`child_id`) REFERENCES `child` (`child_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
