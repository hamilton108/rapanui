{-# LANGUAGE DeriveGeneric #-}

module Rapanui.Critters.Critter where

import Data.Aeson (FromJSON(..))
                   
import GHC.Generics (Generic)                             

import Rapanui.Critters.AcceptRule (AcceptRule)

import Rapanui.Common
  ( Oid
  , OptionTicker
  )

data Critter = 
  Critter 
  { oid :: Oid
  , status :: Int
  , vol :: Int
  , accRules :: [AcceptRule]
  } 
  deriving (Eq,Show,Generic)

instance FromJSON Critter