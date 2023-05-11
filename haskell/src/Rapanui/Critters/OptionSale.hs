{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Critters.OptionSale where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)
import Rapanui.Common
  ( Buy
  , Oid
  , OptionTicker
  )

data SalePayload = SalePayload
  { ticker :: OptionTicker
  , bought :: Buy
  }
  deriving (Eq, Show)

data OptionSale
  = NotActive
  | NoSale
  | Sale SalePayload
  deriving (Eq, Show)
