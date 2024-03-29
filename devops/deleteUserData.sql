DELETE FROM sys_third_party_user WHERE sys_third_party_user.user_id = #{userId};
DELETE FROM t_end_user WHERE t_end_user.id= #{userId};
UPDATE t_end_user SET t_end_user.invitor_id=NULL WHERE t_end_user.invitor_id=#{userId};
DELETE FROM t_house_application_intermediary WHERE t_house_application_intermediary.user_id= #{userId};
DELETE FROM t_house_application_operations WHERE t_house_application_operations.user_id = #{userId};
DELETE FROM t_house_appointment WHERE t_house_appointment.user_id= #{userId};
DELETE FROM t_house_asset_complaint WHERE t_house_asset_complaint.user_id = #{userId};
DELETE FROM t_house_asset_demand_supply WHERE t_house_asset_demand_supply.user_id = #{userId};
DELETE FROM t_house_asset_exchange_request WHERE t_house_asset_exchange_request.user_id = #{userId};
DELETE FROM t_house_asset_match_log WHERE t_house_asset_match_log.owner_user_id = #{userId};
DELETE FROM t_house_asset_match_log WHERE t_house_asset_match_log.matched_user_id = #{userId};
DELETE FROM t_house_browse_log WHERE t_house_browse_log.user_id = #{userId};
DELETE FROM t_house_equity_demand_supply WHERE t_house_equity_demand_supply.user_id= #{userId};
DELETE FROM t_house_rent_asset WHERE t_house_rent_asset.landlord_id= #{userId};
DELETE FROM t_house_subscribe WHERE t_house_subscribe.user_id= #{userId};
DELETE FROM t_house_user_address WHERE t_house_user_address.user_id= #{userId};
DELETE FROM t_house_user_asset WHERE t_house_user_asset.user_id= #{userId};
DELETE FROM t_house_user_community_status WHERE t_house_user_community_status.user_id= #{userId};
DELETE FROM t_house_user_decorate_funiture WHERE t_house_user_decorate_funiture.user_id= #{userId};
DELETE FROM t_house_user_decorate_plan WHERE t_house_user_decorate_plan.user_id= #{userId};