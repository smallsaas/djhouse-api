DELETE FROM t_house_asset_exchange_request WHERE t_house_asset_exchange_request.user_id = #{userId};
DELETE FROM t_house_asset_match_log WHERE t_house_asset_match_log.owner_user_id = #{userId};
DELETE FROM t_house_asset_match_log WHERE t_house_asset_match_log.matched_user_id = #{userId};