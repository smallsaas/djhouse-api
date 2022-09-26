SELECT * FROM t_tenant WHERE locked!=1 AND appid=1;
--  SELECT * FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1);
SELECT * FROM t_house_property_community WHERE tenant_id in (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1);

SELECT * FROM t_house_property_building WHERE community_id IN (SELECT id FROM t_house_property_community WHERE tenant_id in (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_property_building_unit WHERE building_id IN(SELECT id FROM t_house_property_building WHERE community_id IN (SELECT id FROM t_house_property_community WHERE tenant_id in (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1)));

SELECT * FROM t_house_asset WHERE building_id IN(SELECT id FROM t_house_property_building WHERE community_id IN (SELECT id FROM t_house_property_community WHERE tenant_id in (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1)));

SELECT * FROM t_house_application_intermediary WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_application_operations WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_appointment WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_asset_complaint WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_asset_demand_supply WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_asset_exchange_request WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));


SELECT * FROM t_house_asset_match_log WHERE owner_user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_browse_log WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_design_model WHERE community_id IN (SELECT id FROM t_house_property_community WHERE tenant_id in (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_equity_demand_supply WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_subscribe WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_tenant_menu WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1);
SELECT * FROM t_house_user_asset WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));


SELECT * FROM t_house_user_asset WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_community_status WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_decorate_address WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_decorate_funiture WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_decorate_plan WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_note WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_user_tag WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1);

SELECT * FROM t_house_user_tag_relate WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));

SELECT * FROM t_house_vr_type WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1);

SELECT * FROM t_house_vr_picture WHERE type_id IN (SELECT id FROM t_house_vr_type WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1)
    );

SELECT * FROM t_appointment_time WHERE user_id IN (SELECT id FROM t_end_user WHERE org_id IN (SELECT org_id FROM t_tenant WHERE locked!=1 AND appid=1));