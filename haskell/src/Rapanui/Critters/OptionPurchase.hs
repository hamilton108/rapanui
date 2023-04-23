{-# LANGUAGE DeriveGeneric #-}

module Rapanui.Critters.OptionPurchase where

import Data.Aeson (FromJSON(..))
                   
import GHC.Generics (Generic)                             

import Rapanui.Common
  ( Oid(..)
  , OptionTicker(..)
  )

import Rapanui.Critters.Critter (Critter)

data OptionPurchase =
  OptionPurchase 
  { oid :: Oid
  , ticker :: OptionTicker
  , critters :: [Critter]
  } deriving (Eq,Show,Generic)

instance FromJSON OptionPurchase 