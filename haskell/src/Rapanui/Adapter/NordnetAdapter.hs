{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Adapter.NordnetAdapter where

import qualified Data.ByteString.Lazy.Char8 as BL
import qualified Data.Aeson as Aeson

import Rapanui.Critters.OptionPurchase (OptionPurchase)
                   



-- parseCrittersJson :: BL.ByteString -> Maybe [Critter]
-- parseCrittersJson s = 
--   Aeson.decode s

parseCrittersJson :: BL.ByteString -> Maybe [OptionPurchase]
parseCrittersJson s = 
  Aeson.decode s

--  [ Critter (Oid 47) (OptionTicker "NHY9E30")  []
--  ]