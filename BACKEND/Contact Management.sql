PGDMP  "                    }            contacts    12.20    16.4 +    5           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            6           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            7           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            8           1262    16528    contacts    DATABASE     �   CREATE DATABASE contacts WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE contacts;
                postgres    false                        2615    2200    public    SCHEMA     2   -- *not* creating schema, since initdb creates it
 2   -- *not* dropping schema, since initdb creates it
                postgres    false            9           0    0    SCHEMA public    ACL     Q   REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   postgres    false    6            �            1259    16631    ba    TABLE     v   CREATE TABLE public.ba (
    id integer NOT NULL,
    name character varying(255),
    phone character varying(20)
);
    DROP TABLE public.ba;
       public         heap    postgres    false    6            �            1259    16629 	   ba_id_seq    SEQUENCE     �   CREATE SEQUENCE public.ba_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
     DROP SEQUENCE public.ba_id_seq;
       public          postgres    false    204    6            :           0    0 	   ba_id_seq    SEQUENCE OWNED BY     7   ALTER SEQUENCE public.ba_id_seq OWNED BY public.ba.id;
          public          postgres    false    203            �            1259    32972    j    TABLE     u   CREATE TABLE public.j (
    id integer NOT NULL,
    name character varying(255),
    phone character varying(20)
);
    DROP TABLE public.j;
       public         heap    postgres    false    6            �            1259    32970    j_id_seq    SEQUENCE     �   CREATE SEQUENCE public.j_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.j_id_seq;
       public          postgres    false    208    6            ;           0    0    j_id_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE public.j_id_seq OWNED BY public.j.id;
          public          postgres    false    207            �            1259    16626    login    TABLE     b   CREATE TABLE public.login (
    username character varying(50),
    pass character varying(50)
);
    DROP TABLE public.login;
       public         heap    postgres    false    6            �            1259    24780    n    TABLE     u   CREATE TABLE public.n (
    id integer NOT NULL,
    name character varying(255),
    phone character varying(20)
);
    DROP TABLE public.n;
       public         heap    postgres    false    6            �            1259    24778    n_id_seq    SEQUENCE     �   CREATE SEQUENCE public.n_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.n_id_seq;
       public          postgres    false    6    206            <           0    0    n_id_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE public.n_id_seq OWNED BY public.n.id;
          public          postgres    false    205            �            1259    49376    nandu    TABLE     y   CREATE TABLE public.nandu (
    id integer NOT NULL,
    name character varying(255),
    phone character varying(20)
);
    DROP TABLE public.nandu;
       public         heap    postgres    false    6            �            1259    49374    nandu_id_seq    SEQUENCE     �   CREATE SEQUENCE public.nandu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.nandu_id_seq;
       public          postgres    false    212    6            =           0    0    nandu_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.nandu_id_seq OWNED BY public.nandu.id;
          public          postgres    false    211            �            1259    49356    p    TABLE     u   CREATE TABLE public.p (
    id integer NOT NULL,
    name character varying(255),
    phone character varying(20)
);
    DROP TABLE public.p;
       public         heap    postgres    false    6            �            1259    49354    p_id_seq    SEQUENCE     �   CREATE SEQUENCE public.p_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    DROP SEQUENCE public.p_id_seq;
       public          postgres    false    210    6            >           0    0    p_id_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE public.p_id_seq OWNED BY public.p.id;
          public          postgres    false    209            �
           2604    16634    ba id    DEFAULT     ^   ALTER TABLE ONLY public.ba ALTER COLUMN id SET DEFAULT nextval('public.ba_id_seq'::regclass);
 4   ALTER TABLE public.ba ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    204    203    204            �
           2604    32975    j id    DEFAULT     \   ALTER TABLE ONLY public.j ALTER COLUMN id SET DEFAULT nextval('public.j_id_seq'::regclass);
 3   ALTER TABLE public.j ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    208    207    208            �
           2604    24783    n id    DEFAULT     \   ALTER TABLE ONLY public.n ALTER COLUMN id SET DEFAULT nextval('public.n_id_seq'::regclass);
 3   ALTER TABLE public.n ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    205    206    206            �
           2604    49379    nandu id    DEFAULT     d   ALTER TABLE ONLY public.nandu ALTER COLUMN id SET DEFAULT nextval('public.nandu_id_seq'::regclass);
 7   ALTER TABLE public.nandu ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    211    212    212            �
           2604    49359    p id    DEFAULT     \   ALTER TABLE ONLY public.p ALTER COLUMN id SET DEFAULT nextval('public.p_id_seq'::regclass);
 3   ALTER TABLE public.p ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    209    210    210            *          0    16631    ba 
   TABLE DATA           -   COPY public.ba (id, name, phone) FROM stdin;
    public          postgres    false    204   U(       .          0    32972    j 
   TABLE DATA           ,   COPY public.j (id, name, phone) FROM stdin;
    public          postgres    false    208   y(       (          0    16626    login 
   TABLE DATA           /   COPY public.login (username, pass) FROM stdin;
    public          postgres    false    202   �(       ,          0    24780    n 
   TABLE DATA           ,   COPY public.n (id, name, phone) FROM stdin;
    public          postgres    false    206   �(       2          0    49376    nandu 
   TABLE DATA           0   COPY public.nandu (id, name, phone) FROM stdin;
    public          postgres    false    212   
)       0          0    49356    p 
   TABLE DATA           ,   COPY public.p (id, name, phone) FROM stdin;
    public          postgres    false    210   ')       ?           0    0 	   ba_id_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('public.ba_id_seq', 1, true);
          public          postgres    false    203            @           0    0    j_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('public.j_id_seq', 3, true);
          public          postgres    false    207            A           0    0    n_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('public.n_id_seq', 1, true);
          public          postgres    false    205            B           0    0    nandu_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.nandu_id_seq', 1, false);
          public          postgres    false    211            C           0    0    p_id_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('public.p_id_seq', 1, true);
          public          postgres    false    209            �
           2606    16636 
   ba ba_pkey 
   CONSTRAINT     H   ALTER TABLE ONLY public.ba
    ADD CONSTRAINT ba_pkey PRIMARY KEY (id);
 4   ALTER TABLE ONLY public.ba DROP CONSTRAINT ba_pkey;
       public            postgres    false    204            �
           2606    32977    j j_pkey 
   CONSTRAINT     F   ALTER TABLE ONLY public.j
    ADD CONSTRAINT j_pkey PRIMARY KEY (id);
 2   ALTER TABLE ONLY public.j DROP CONSTRAINT j_pkey;
       public            postgres    false    208            �
           2606    24785    n n_pkey 
   CONSTRAINT     F   ALTER TABLE ONLY public.n
    ADD CONSTRAINT n_pkey PRIMARY KEY (id);
 2   ALTER TABLE ONLY public.n DROP CONSTRAINT n_pkey;
       public            postgres    false    206            �
           2606    49381    nandu nandu_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.nandu
    ADD CONSTRAINT nandu_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.nandu DROP CONSTRAINT nandu_pkey;
       public            postgres    false    212            �
           2606    49361    p p_pkey 
   CONSTRAINT     F   ALTER TABLE ONLY public.p
    ADD CONSTRAINT p_pkey PRIMARY KEY (id);
 2   ALTER TABLE ONLY public.p DROP CONSTRAINT p_pkey;
       public            postgres    false    210            *      x�3��洄�=... )I�      .   %   x�3��ᴀ.c��lNC#cS3sKC�=... �v�      (   '   x�KJ�LJ���������*�,��K�K)������� ���      ,      x�3���44�A�=... &S      2      x������ � �      0      x������ � �     