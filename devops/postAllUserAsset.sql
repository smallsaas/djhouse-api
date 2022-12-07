DELETE  FROM t_house_application_intermediary;
DELETE  FROM t_house_application_operations ;
DELETE  FROM t_house_appointment WHERE address_id in(SELECT id FROM t_house_rent_asset WHERE asset_id IS NOT NULL);
DELETE  FROM t_house_asset_complaint;
DELETE  FROM t_house_asset_demand_supply ;
DELETE  FROM t_house_asset_exchange_request ;
DELETE  FROM t_house_asset_log ;
DELETE  FROM t_house_asset_match_log ;
DELETE  FROM t_house_equity_demand_supply ;
DELETE  FROM t_house_rent_asset WHERE asset_id IS NOT NULL;
DELETE  FROM t_house_subscribe WHERE subscribe_id in(SELECT id FROM t_house_rent_asset WHERE asset_id IS NOT NULL);
DELETE  FROM t_house_unlike_log;
DELETE  FROM t_house_user_address ;
DELETE  FROM t_house_user_asset ;
DELETE  FROM t_house_user_community_status ;
DELETE  FROM t_house_user_decorate_funiture ;
DELETE  FROM t_house_user_decorate_plan ;
DELETE  FROM t_house_user_note ;
DELETE  FROM t_house_user_tag;
DELETE  FROM t_house_user_tag_relate;