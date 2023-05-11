{-# LANGUAGE DeriveGeneric #-}
{-# LANGUAGE OverloadedStrings #-}
{-# LANGUAGE StrictData #-}

module Rapanui.Common where

import Data.Aeson (FromJSON (..))
import GHC.Generics (Generic)

import Control.Monad.Catch
  ( MonadCatch
  , MonadMask
  , MonadThrow
  )
import Control.Monad.Reader
  ( MonadIO
  , MonadReader
  , ReaderT
  )
import Control.Monad.State
  ( MonadState
  , StateT
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

instance FromJSON OptionTicker

instance FromJSON Oid

instance FromJSON Pid

instance FromJSON Cid

instance FromJSON Rtyp

instance FromJSON Buy

instance FromJSON Sell

data Env = Env
  { getHost :: NordnetHost
  , getPort :: NordnetPort
  , getCt :: CritterType
  , getInterval :: Int
  }
  deriving (Show)

data PurchaseCtx = PurchaseCtx
  { x :: Int
  }

-- type RCTX a = Reader PurchaseCtx a

demoEnv :: Env
demoEnv =
  Env (NordnetHost "localhost") (NordnetPort 8082) (CritterType "11") 0

newtype REIO a = REIO
  { runApp :: ReaderT Env IO a
  }
  deriving
    ( Functor
    , Applicative
    , Monad
    , MonadIO
    , MonadThrow
    , MonadCatch
    , MonadMask
    , MonadReader Env
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
