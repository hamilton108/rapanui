{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE NamedFieldPuns #-}

module Rapanui.Critters.OptionPurchase where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
import Rapanui.Common
  ( Oid
  , OptionTicker (..)
  , Ask
  )
import Rapanui.Critters.Critter (Critter)
import qualified Rapanui.Critters.Critter as C
import qualified Rapanui.OptionSale.OptionSaleItem as S
import Rapanui.StockOption
  ( StockOption (..)
  )

data OptionPurchase = OptionPurchase
  { oid :: Oid
  , ticker :: OptionTicker
  , price :: Ask
  , critters :: [Critter]
  }
  deriving (Eq, Show, Generic)

instance FromJSON OptionPurchase

-- applyCritter :: OptionTicker -> StockOption -> Critter -> S.OptionSale
-- applyCritter t o c =
--  C.applyAcceptRules c o
-- S.OptionSale t False Nothing

applyPurchase :: OptionPurchase -> StockOption -> [S.OptionSale]
applyPurchase OptionPurchase{price, critters} opx =
  map (C.applyCritter price opx) critters
