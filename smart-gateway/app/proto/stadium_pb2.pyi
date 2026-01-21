from google.protobuf.internal import containers as _containers
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from collections.abc import Iterable as _Iterable, Mapping as _Mapping
from typing import ClassVar as _ClassVar, Optional as _Optional, Union as _Union

DESCRIPTOR: _descriptor.FileDescriptor

class EventSearchRequest(_message.Message):
    __slots__ = ("query",)
    QUERY_FIELD_NUMBER: _ClassVar[int]
    query: str
    def __init__(self, query: _Optional[str] = ...) -> None: ...

class EventIdRequest(_message.Message):
    __slots__ = ("id",)
    ID_FIELD_NUMBER: _ClassVar[int]
    id: int
    def __init__(self, id: _Optional[int] = ...) -> None: ...

class EventListResponse(_message.Message):
    __slots__ = ("events",)
    EVENTS_FIELD_NUMBER: _ClassVar[int]
    events: _containers.RepeatedCompositeFieldContainer[EventResponse]
    def __init__(self, events: _Optional[_Iterable[_Union[EventResponse, _Mapping]]] = ...) -> None: ...

class EventResponse(_message.Message):
    __slots__ = ("id", "title", "category", "date_time", "location", "price", "available_seats", "status")
    ID_FIELD_NUMBER: _ClassVar[int]
    TITLE_FIELD_NUMBER: _ClassVar[int]
    CATEGORY_FIELD_NUMBER: _ClassVar[int]
    DATE_TIME_FIELD_NUMBER: _ClassVar[int]
    LOCATION_FIELD_NUMBER: _ClassVar[int]
    PRICE_FIELD_NUMBER: _ClassVar[int]
    AVAILABLE_SEATS_FIELD_NUMBER: _ClassVar[int]
    STATUS_FIELD_NUMBER: _ClassVar[int]
    id: int
    title: str
    category: str
    date_time: str
    location: str
    price: float
    available_seats: int
    status: str
    def __init__(self, id: _Optional[int] = ..., title: _Optional[str] = ..., category: _Optional[str] = ..., date_time: _Optional[str] = ..., location: _Optional[str] = ..., price: _Optional[float] = ..., available_seats: _Optional[int] = ..., status: _Optional[str] = ...) -> None: ...
