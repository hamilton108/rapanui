{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Critters.Critter where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
import Rapanui.Common
  ( Oid
  , OptionTicker (..)
  , Sell
  )
import Rapanui.Critters.AcceptRule (AcceptRule)
import qualified Rapanui.Critters.AcceptRule as A
import Rapanui.OptionSale.OptionSaleItem
  ( OptionSale (..)
  )
import Rapanui.StockOption (StockOption)

data Critter = Critter
  { oid :: Oid
  , status :: Int
  , vol :: Int
  , accRules :: [AcceptRule]
  }
  deriving (Eq, Show, Generic)

instance FromJSON Critter

extractSale :: [OptionSale] -> OptionSale
extractSale sales =
  let
    hits = [x | x@(Sale{}) <- sales] :: [OptionSale]
  in
    case hits of
      [] -> NoSale
      (x : _) -> x

applyCritter :: Sell -> StockOption -> Critter -> OptionSale
applyCritter s o c =
  if status c == 7
    then extractSale $ map (A.apply s o) (accRules c)
    else NotActive
