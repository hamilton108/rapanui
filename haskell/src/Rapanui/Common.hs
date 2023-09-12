{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}

module Rapanui.Common where

import Data.Time.LocalTime 
  ( TimeOfDay(..)
  )
import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)

-- import Control.Monad.Catch
--   ( MonadCatch
--   , MonadMask
--   , MonadThrow
--   )
import Control.Monad.Reader
  ( MonadIO
  , MonadReader
  , ReaderT
  )

import Data.Text (Text)

newtype OptionTicker = OptionTicker Text deriving (Eq, Show, Generic)

-- newtype StockTicker = StockTicker String deriving (Eq,Show,Generic)

newtype Oid = Oid Int deriving (Eq, Show, Generic)

newtype Pid = Pid Int deriving (Eq, Show, Generic)

newtype Cid = Cid Int deriving (Eq, Show, Generic)

newtype Rtyp = Rtyp Int deriving (Eq, Show, Generic)

newtype CritterType = CritterType Text deriving (Eq, Show)

newtype NordnetHost = NordnetHost Text deriving (Show)

newtype NordnetPort = NordnetPort Int deriving (Show)

newtype Buy = Buy Double deriving (Eq, Ord, Show, Generic)

newtype Sell = Sell Double deriving (Eq, Show, Generic)

newtype PosixTimeInt = PosixTimeInt Int deriving (Show)

newtype Iso8601 = Iso8601 String deriving (Show)

newtype MarketOpen = MarketOpen TimeOfDay deriving (Show)

newtype MarketClose = MarketClose TimeOfDay deriving (Show)

instance FromJSON OptionTicker

instance FromJSON Oid

instance FromJSON Pid

instance FromJSON Cid

instance FromJSON Rtyp

instance FromJSON Buy

instance FromJSON Sell

-- data MarketOpenClose = MarketOpenClose
--   { open :: TimeOfDay 
--   , close :: TimeOfDay 
--   }

data TimeInfo = TimeInfo
  { posixTimeInt :: PosixTimeInt
  , iso8601 :: Iso8601
  }
  deriving (Show)

data Env = Env
  { getHost :: NordnetHost
  , getPort :: NordnetPort
  , getCt :: CritterType
  , getOpen :: MarketOpen 
  , getClose :: MarketClose
  , getInterval :: Int
  }
  deriving (Show)

-- type RCTX a = Reader PurchaseCtx a

demoEnv :: Env
demoEnv = Env 
  (NordnetHost "localhost") 
  (NordnetPort 8082) 
  (CritterType "11") 
  (MarketOpen $ TimeOfDay 10 32 0)
  (MarketClose $ TimeOfDay 10 55 0)
  0

newtype REIO a = REIO
  { runApp :: ReaderT Env IO a
  }
  deriving
    ( Functor
    , Applicative
    , Monad
    , MonadIO
    , MonadReader Env
    -- , MonadThrow
    -- , MonadCatch
    -- , MonadMask
    )

-- newtype REIO2 a = REIO2
--   { runApp2 :: ReaderT Env (StateT AppState IO) a
--   }
--   deriving
--     ( Functor
--     , Applicative
--     , Monad
--     , MonadIO
--     , MonadThrow
--     , MonadCatch
--     , MonadReader Env
--     , MonadState AppState
--     )
